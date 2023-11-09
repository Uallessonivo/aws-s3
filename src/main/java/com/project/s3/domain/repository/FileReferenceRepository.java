package com.project.s3.domain.repository;

import com.project.s3.domain.model.FileReference;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FileReferenceRepository extends JpaRepository<FileReference, UUID> {
}
