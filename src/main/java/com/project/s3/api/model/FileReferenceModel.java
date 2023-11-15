package com.project.s3.api.model;

import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class FileReferenceModel {
    private UUID id;
    private boolean publicAccessible;
    private String name;
    private String contentType;
    private Long contentTLength;
    private String downloadUrl;

}
