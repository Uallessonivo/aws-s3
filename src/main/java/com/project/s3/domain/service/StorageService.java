package com.project.s3.domain.service;

import com.project.s3.domain.model.FileReference;
import com.project.s3.domain.repository.FileReferenceRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.net.URL;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StorageService {

    private final CloudStorageProvider cloudStorageProvider;
    private final FileReferenceRepository fileReferenceRepository;

    public UploadRequestResult generateUploadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        fileReferenceRepository.save(fileReference);
        URL presignedUploadUrl = cloudStorageProvider.generatePresignedUploadUrl(fileReference);
        return new UploadRequestResult(fileReference.getId(), presignedUploadUrl.toString());
    }

    public DownloadRequestResult generateDownloadUrl(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        URL url = cloudStorageProvider.generatePresignedDownloadUrl(fileReference);
        return new DownloadRequestResult(url.toString());
    }

    public boolean fileExists(FileReference fileReference) {
        Objects.requireNonNull(fileReference);
        return this.cloudStorageProvider.fileExists(fileReference.getPath());
    }
}
