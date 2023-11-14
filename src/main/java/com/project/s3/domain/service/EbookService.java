package com.project.s3.domain.service;

import com.project.s3.domain.exception.BusinessExcpetion;
import com.project.s3.domain.model.Ebook;
import com.project.s3.domain.model.FileReference;
import com.project.s3.domain.repository.EbookRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
@RequiredArgsConstructor
public class EbookService {
    private final EbookRepository ebookRepository;
    private final StorageService storageService;

    @Transactional
    public Ebook create(Ebook ebook) {
        Objects.requireNonNull(ebook);

        if (storageService.fileExists(ebook.getCover())) {
            throw new BusinessExcpetion(String.format("file was not found: %s", ebook.getCover().getId()));
        }

        if (storageService.fileExists(ebook.getAttachment())) {
            throw new BusinessExcpetion(String.format("file was not found: %s", ebook.getCover().getId()));
        }

        if (!FileReference.Type.IMAGE.equals(ebook.getCover().getType())) {
            throw new BusinessExcpetion(String.format("file is not an image: %s", ebook.getCover().getId()));
        }

        if (!FileReference.Type.DOCUMENT.equals(ebook.getAttachment().getType())) {
            throw new BusinessExcpetion(String.format("file is not an document: %s", ebook.getAttachment().getId()));
        }

        ebook.getCover().setTemp(false);
        ebook.getAttachment().setTemp(false);

        ebookRepository.save(ebook);

        return ebook;
    }

    @Transactional
    public Ebook update(Ebook ebookUpdated) {
        Objects.requireNonNull(ebookUpdated);

        Ebook existingEbook = ebookRepository.findById(ebookUpdated.getId())
                .orElseThrow(EntityNotFoundException::new);

        existingEbook.update(ebookUpdated);
        ebookRepository.save(existingEbook);
        ebookRepository.flush();

        return existingEbook;
    }
}
