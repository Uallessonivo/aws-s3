package com.project.s3.domain.model;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Ebook {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "char(36)")
    @EqualsAndHashCode.Include
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private String title;

    private String author;

    @OneToOne(cascade = CascadeType.ALL)
    private FileReference cover;

    @OneToOne(cascade = CascadeType.ALL)
    private FileReference attachment;

    protected Ebook() {
        // for hibernate
    }

    public Ebook(UUID id, OffsetDateTime createdAt, String title, String author, FileReference cover, FileReference attachment) {
        Objects.requireNonNull(title);
        Objects.requireNonNull(author);
        Objects.requireNonNull(cover);
        Objects.requireNonNull(attachment);

        this.id = id;
        this.createdAt = createdAt;
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.attachment = attachment;
    }

    public void update(Ebook ebookUpdated) {
        Objects.requireNonNull(ebookUpdated);
        this.title = ebookUpdated.title;
        this.author = ebookUpdated.author;
        this.cover = ebookUpdated.cover;
        this.attachment = ebookUpdated.attachment;
    }
}
