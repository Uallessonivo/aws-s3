package com.project.s3.api.assembler;

import com.project.s3.api.model.EbookModel;
import com.project.s3.api.model.FileReferenceModel;
import com.project.s3.core.StorageProperties;
import com.project.s3.domain.model.Ebook;
import com.project.s3.domain.model.FileReference;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.net.URL;

@Component
@RequiredArgsConstructor
public class EbookModelAssembler {

    private final StorageProperties storageProperties;

    public EbookModel toModel(Ebook ebook) {
        EbookModel.EbookModelBuilder builder = EbookModel.builder()
                .id(ebook.getId())
                .author(ebook.getAuthor())
                .title(ebook.getTitle());

        if (ebook.getCover() != null) {
            builder.cover(createCover(ebook.getCover()));
        }

        if (ebook.getAttachment() != null) {
            builder.cover(createAttachment(ebook.getAttachment()));
        }

        return builder.build();
    }

    private FileReferenceModel createCover(FileReference cover) {
        String downloadUrl = storageProperties.getImage().getDownloadUrl().toString() + "/" + cover.getPath();

        return FileReferenceModel.builder()
                .id(cover.getId())
                .name(cover.getName())
                .contentType(cover.getContentType())
                .contentTLength(cover.getContentLength())
                .publicAccessible(cover.isPublicAccessible())
                .downloadUrl(downloadUrl)
                .build();
    }

    private FileReferenceModel createAttachment(FileReference attachment) {
        String downloadUrl = storageProperties.getDocument().getDownloadUrl().toString() + "/" + attachment.getPath();

        return FileReferenceModel.builder()
                .id(attachment.getId())
                .name(attachment.getName())
                .contentType(attachment.getContentType())
                .contentTLength(attachment.getContentLength())
                .publicAccessible(attachment.isPublicAccessible())
                .downloadUrl(downloadUrl)
                .build();
    }
}
