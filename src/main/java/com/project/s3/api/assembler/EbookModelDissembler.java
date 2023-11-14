package com.project.s3.api.assembler;

import com.project.s3.api.model.EbookRequest;
import com.project.s3.domain.exception.BusinessExcpetion;
import com.project.s3.domain.model.Ebook;
import com.project.s3.domain.model.FileReference;
import com.project.s3.domain.repository.FileReferenceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class EbookModelDissembler {
    private final FileReferenceRepository fileReferenceRepository;

    public Ebook toDomain(EbookRequest ebookRequest) {
        return Ebook.builder()
                .title(ebookRequest.getTitle())
                .author(ebookRequest.getAuthor())
                .cover(getFileReference(ebookRequest.getCoverId()))
                .attachment(getFileReference(ebookRequest.getAttachmentId()))
                .build();
    }

    public Ebook toDomain(EbookRequest ebookRequest, UUID ebookId) {
        return Ebook.builder()
                .id(ebookId)
                .title(ebookRequest.getTitle())
                .author(ebookRequest.getAuthor())
                .cover(getFileReference(ebookRequest.getCoverId()))
                .attachment(getFileReference(ebookRequest.getAttachmentId()))
                .build();
    }

    private FileReference getFileReference(UUID id) {
        return fileReferenceRepository.findById(id)
                .orElseThrow(() -> new BusinessExcpetion(String.format("File reference not found %s", id)));
    }
}
