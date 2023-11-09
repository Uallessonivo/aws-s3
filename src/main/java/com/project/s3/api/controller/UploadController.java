package com.project.s3.api.controller;

import com.project.s3.api.model.UploadDocumentRequest;
import com.project.s3.api.model.UploadImageRequest;
import com.project.s3.domain.service.StorageService;
import com.project.s3.domain.service.UploadRequestResult;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/uploads")
@AllArgsConstructor
public class UploadController {

    private final StorageService storageService;

    @PostMapping("/documents")
    public UploadRequestResult newDocumentUploadRequest(
            @RequestBody @Valid UploadDocumentRequest request
    ) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }

    @PostMapping("/images")
    public UploadRequestResult newImageUploadRequest(
            @RequestBody @Valid UploadImageRequest request
    ) {
        return this.storageService.generateUploadUrl(request.toDomain());
    }
}
