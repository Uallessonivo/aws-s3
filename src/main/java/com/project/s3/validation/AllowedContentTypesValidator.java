package com.project.s3.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.AllArgsConstructor;
import org.hibernate.validator.internal.engine.constraintvalidation.ConstraintValidatorContextImpl;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class AllowedContentTypesValidator implements ConstraintValidator<AllowedContentTypes, String> {
    private String[] allowedTypes = {};

    public final void initialize(final AllowedContentTypes annotation) {
        this.allowedTypes = annotation.value();
    }

    @Override
    public boolean isValid(final String type, final ConstraintValidatorContext context) {
        if (type == null || type.isBlank()) {
            return true;
        }

        if (allowedTypes.length == 0) {
            return true;
        }

        for (final String allowedType : this.allowedTypes) {
            if (type.equals(allowedType)) {
                return true;
            }
        }

        var contextImpl = (ConstraintValidatorContextImpl) context;
        contextImpl.addMessageParameter("types", String.join(", ", allowedTypes));

        return false;
    }
}
