package com.project.s3.api.model;

import com.project.s3.domain.model.FileReference;
import com.project.s3.validation.AllowedContentTypes;
import com.project.s3.validation.AllowedFileExtensions;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UploadDocumentRequest {

    @NotBlank
    @AllowedFileExtensions("pdf")
    private String fileName;

    @NotBlank
    @AllowedContentTypes("application/pdf")
    private String contentType;

    @NotNull
    @Min(1)
    private Long contentLength;

    public FileReference toDomain() {
        return FileReference.builder()
                .name(this.fileName)
                .contentType(this.contentType)
                .contentLength(this.contentLength)
                .type(FileReference.Type.DOCUMENT)
                .build();
    }
}
