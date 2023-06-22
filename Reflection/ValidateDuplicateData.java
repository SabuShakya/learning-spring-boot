package com.f1soft.billpay.common.annotation;


import com.f1soft.billpay.common.validator.DuplicateDataValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author - Sabu Shakya
 */
@Documented
@Constraint(validatedBy = DuplicateDataValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidateDuplicateData {

    String message() default "Duplicate Data";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

   String fieldName() default "";
}
