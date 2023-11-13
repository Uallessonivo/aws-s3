package com.project.s3.api.controller;

import com.project.s3.api.assembler.EbookModelAssembler;
import com.project.s3.api.assembler.EbookModelDissembler;
import com.project.s3.api.model.EbookModel;
import com.project.s3.api.model.EbookRequest;
import com.project.s3.domain.model.Ebook;
import com.project.s3.domain.repository.EbookRepository;
import com.project.s3.domain.service.EbookService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ebooks")
@RequiredArgsConstructor
public class EbookController {
    private final EbookService ebookService;
    private final EbookRepository ebookRepository;
    private final EbookModelAssembler ebookModelAssembler;
    private final EbookModelDissembler ebookModelDissembler;

    @PostMapping
    public EbookModel create(@RequestBody @Valid EbookRequest ebookRequest) {
        Ebook ebook = ebookModelDissembler.toDomain(ebookRequest);
        return ebookModelAssembler.toModel(ebookService.create(ebook));
    }

    @GetMapping("/{ebookId}")
    public EbookModel getById(@PathVariable UUID ebookId) {
        return ebookModelAssembler.toModel(ebookRepository.findById(ebookId).orElseThrow(EntityNotFoundException::new));
    }

    @PutMapping("/{ebookId}")
    public EbookModel update(@PathVariable UUID ebookId, @RequestBody @Valid EbookRequest ebookRequest) {
        Ebook ebook = ebookModelDissembler.toDomain(ebookRequest, ebookId);
        return ebookModelAssembler.toModel(ebookService.update(ebook));
    }

    @GetMapping
    public List<EbookModel> list() {
        return ebookRepository.findAll()
                .stream()
                .map(ebookModelAssembler::toModel)
                .toList();
    }
}
