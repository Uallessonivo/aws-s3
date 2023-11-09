package com.project.s3.domain.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.OffsetDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@EqualsAndHashCode
@Builder
@Entity
public class FileReference {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(name = "id", columnDefinition = "char(36)")
    private UUID id;

    @CreationTimestamp
    private OffsetDateTime createdAt;

    private String name;

    private String contentType;

    private Long contentLength;

    @Builder.Default
    private boolean temp = true;

    @Enumerated(EnumType.STRING)
    private Type type;

    protected FileReference() {
    }

    public FileReference(UUID id, OffsetDateTime createdAt, String name, String contentType, Long contentLength, boolean temp, Type type) {
        Objects.requireNonNull(name);
        Objects.requireNonNull(contentType);
        Objects.requireNonNull(contentLength);
        Objects.requireNonNull(type);

        this.id = id;
        this.createdAt = createdAt;
        this.name = name;
        this.contentType = contentType;
        this.contentLength = contentLength;
        this.temp = temp;
        this.type = type;
    }

    public void setTemp(boolean temp) {
        this.temp = temp;
    }

    @Getter
    @AllArgsConstructor
    public enum Type {
        DOCUMENT(false),
        IMAGE(true);

        private final boolean publicAccessible;
    }

    public boolean isPublicAccessible() {
        return this.type.publicAccessible;
    }
}