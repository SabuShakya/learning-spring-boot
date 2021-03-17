package com.f1soft.billpay.common.validator;

import com.f1soft.billpay.common.annotation.ValidateDuplicateData;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.Field;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author - Sabu Shakya
 */
public class DuplicateDataValidator implements ConstraintValidator<ValidateDuplicateData, List<?>> {

    private String fieldName;

    @Override
    public void initialize(ValidateDuplicateData constraintAnnotation) {
        this.fieldName = constraintAnnotation.fieldName();
    }

    @Override
    public boolean isValid(List<?> objectList, ConstraintValidatorContext constraintValidatorContext) {

        Long count = objectList.stream()
                .map(getSpecifiedFieldValue)
                .collect(Collectors.groupingBy(
                        Function.identity(),
                        Collectors.counting()
                ))
                .values()
                .stream()
                .filter(x -> x > 1)
                .count();

        if (count > 0)
            return false;

        return true;
    }

    public Function<Object, Object> getSpecifiedFieldValue = object -> {
        String[] fieldnames = this.fieldName.split("[.]");
        Object fieldObject = object;
        for (String parameterName : fieldnames) {
            if (fieldObject instanceof Collection<?>) {
                List<Object> listData = (List<Object>) fieldObject;
                fieldObject = listData.stream()
                        .map(o -> getFieldValue.apply(o, parameterName))
                        .collect(Collectors.toList());
            } else {
                Object paramObj = getFieldValue.apply(fieldObject, parameterName);
                fieldObject = paramObj;
            }
        }
        return fieldObject;
    };

    public static BiFunction<Object, String, Object> getFieldValue = (object, fieldname) -> {
        Object fieldValue = null;
        try {
            Field field = object.getClass().getDeclaredField(fieldname);
            field.setAccessible(true);
            fieldValue = field.get(object);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return fieldValue;
    };
}
