package com.watches.watch.validation;


import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = FountainCustomValidator.class)
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidFountainMimeTypeConstraint {

    String message() default "Mime type is not supported";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
    String field();
}
