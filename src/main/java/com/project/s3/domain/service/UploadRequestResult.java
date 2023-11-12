package com.project.s3.domain.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UploadRequestResult {
    private UUID fileReferenceId;
    private String uploadSignedUrl;
}
