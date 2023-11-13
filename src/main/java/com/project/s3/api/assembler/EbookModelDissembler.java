package com.project.s3.api.assembler;

import com.project.s3.api.model.EbookRequest;
import com.project.s3.domain.model.Ebook;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EbookModelDissembler {
    public Ebook toDomain(EbookRequest ebookRequest) {
        return Ebook.builder()
                .title(ebookRequest.getTitle())
                .author(ebookRequest.getAuthor())
                .build();
    }

    public Ebook toDomain(EbookRequest ebookRequest, UUID ebookId) {
        return Ebook.builder()
                .id(ebookId)
                .title(ebookRequest.getTitle())
                .author(ebookRequest.getAuthor())
                .build();
    }
}
