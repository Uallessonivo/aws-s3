package com.project.s3.api.model;

import com.project.s3.domain.model.FileReference;
import com.project.s3.validation.AllowedFileExtensions;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UploadDocumentRequest {

    @NotBlank
    @AllowedFileExtensions("pdf")

    @NotBlank
    private String fileName;
    private String contentType;
    private Long contentLength;

    public FileReference toDomain() {
        return null;
    }
}
