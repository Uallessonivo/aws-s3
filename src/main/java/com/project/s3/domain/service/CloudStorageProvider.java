package com.project.s3.domain.service;

import com.project.s3.domain.model.FileReference;
import java.net.URL;

public interface CloudStorageProvider {
    URL generateUploadUrl(FileReference fileReference);
    URL generateDownloadUrl(FileReference fileReference);
}
