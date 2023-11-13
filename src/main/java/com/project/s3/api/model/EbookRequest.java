package com.project.s3.api.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Data
public class EbookRequest {
    @NotBlank
    private String title;
    @NotBlank
    private String author;
    @NotNull
    private UUID coverId;
    @NotNull
    private UUID attachmentId;
}
