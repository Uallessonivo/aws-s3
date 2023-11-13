package com.project.s3.domain.service;

import com.project.s3.domain.model.Ebook;
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

    @Transactional
    public Ebook create(Ebook ebook) {
        Objects.requireNonNull(ebook);

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
