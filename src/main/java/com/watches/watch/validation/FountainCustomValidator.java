package com.watches.watch.validation;

import com.watches.decoder.MimeType;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import static com.watches.decoder.Base64Decoder.decodeMimeType;

@Service
@Scope(BeanDefinition.SCOPE_PROTOTYPE)
@RequiredArgsConstructor
@Transactional(readOnly = true, propagation = Propagation.REQUIRES_NEW)
public class FountainCustomValidator implements ConstraintValidator<ValidFountainMimeTypeConstraint, Object> {

    private String field;

    public void initialize(ValidFountainMimeTypeConstraint constraintAnnotation) {
        this.field = constraintAnnotation.field();
    }

    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object fieldValue = new BeanWrapperImpl(value)
                .getPropertyValue(field);
        String mimeType = decodeMimeType((String) fieldValue);
        return MimeType.isValidType(mimeType);
    }
}
