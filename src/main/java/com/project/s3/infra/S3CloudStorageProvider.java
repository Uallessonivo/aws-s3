package com.project.s3.infra;

import com.project.s3.core.StorageProperties;
import com.project.s3.domain.exception.StorageCloudException;
import com.project.s3.domain.model.FileReference;
import com.project.s3.domain.service.CloudStorageProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import software.amazon.awssdk.awscore.AwsRequestOverrideConfiguration;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.presigner.S3Presigner;
import software.amazon.awssdk.services.s3.presigner.model.GetObjectPresignRequest;
import software.amazon.awssdk.services.s3.presigner.model.PutObjectPresignRequest;

import java.net.URL;
import java.time.Duration;

@Component
@AllArgsConstructor
@Slf4j
public class S3CloudStorageProvider implements CloudStorageProvider {
    private final S3Client s3Client;
    private final S3Presigner s3Presigner;
    private final StorageProperties storageProperties;

    @Override
    public URL generatePresignedUploadUrl(FileReference fileReference) {
        AwsRequestOverrideConfiguration.Builder builder = AwsRequestOverrideConfiguration.builder();

        if (fileReference.isPublicAccessible()) {
            builder.putRawQueryParameter("x-amz-acl", "public-read");
        }

        PutObjectRequest objectRequest = PutObjectRequest.builder()
                .bucket(getBucket())
                .key(fileReference.getPath())
                .contentType(fileReference.getContentType())
                .contentLength(fileReference.getContentLength())
                .acl(fileReference.isPublicAccessible() ? "public-read" : null)
                .overrideConfiguration(builder.build())
                .build();

        PutObjectPresignRequest presignRequest = PutObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .putObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignPutObject(presignRequest).url();
    }

    @Override
    public URL generatePresignedDownloadUrl(FileReference fileReference) {
        GetObjectRequest objectRequest = GetObjectRequest.builder()
                .bucket(getBucket())
                .key(fileReference.getPath())
                .build();

        GetObjectPresignRequest objectPresignRequest = GetObjectPresignRequest.builder()
                .signatureDuration(Duration.ofMinutes(30))
                .getObjectRequest(objectRequest)
                .build();

        return s3Presigner.presignGetObject(objectPresignRequest).url();
    }

    @Override
    public boolean fileExists(String path) {
        if (path == null || path.isEmpty()) {
            return false;
        }
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket(getBucket())
                .key(path)
                .build();

        try {
            s3Client.getObject(request);
            return true;
        } catch (NoSuchKeyException e) {
            log.warn(String.format("file not found: %s", path));
            return false;
        }
    }

    @Override
    public void moveFile(String path, String s) {
        CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                .sourceKey(path)
                .destinationKey(s)
                .sourceBucket(getBucket())
                .destinationBucket(getBucket())
                .build();

        try {
            s3Client.copyObject(copyObjectRequest);
        } catch (Exception e) {
            log.error(String.format("error moving file from %s to %s", path, s), e);
            throw new StorageCloudException(String.format("error moving file from %s to %s", path, s));
        }

        removeFile(path);
    }

    @Override
    public void removeFile(String fromPath) {
        DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                .bucket(getBucket())
                .key(fromPath)
                .build();

        try {
            s3Client.deleteObject(deleteObjectRequest);
        } catch (Exception e) {
            log.error(String.format("error removing file %s", fromPath), e);
            throw new StorageCloudException(String.format("error removing file %s", fromPath));
        }
    }

    private String getBucket() {
        return storageProperties.getS3().getBucket();
    }
}
