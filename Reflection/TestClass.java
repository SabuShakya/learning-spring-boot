package com.f1soft.billpay.common.validator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.function.BiFunction;
import java.util.stream.Collectors;

public class TestClass {

    public static void main(String[] args) {
//        Human student = new Student();
        Person person = new Person();
        person.setName("Sabu");
        person.setAddress(new Address("Patan"));

        Test test = new Test();
        List<Person> personList = new ArrayList<>();
        personList.add(person);

        test.setPerson(person);
        test.setPersonList(personList);

        Object value = getSpecifiedFieldValue("personList.address.street", test);
        Object value1 = getSpecifiedFieldValue("person.address.street", test);
        Object value2 = getSpecifiedFieldValue("name", person);
        System.out.println("value =================> " + value);
        System.out.println("value1 =================> " + value1);
        System.out.println("value2 =================> " + value2);
    }

    public static Object getSpecifiedFieldValue(String fieldname, Object obj) {
        String[] fieldnames = fieldname.split("[.]");
        Object fieldObject = obj;
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
    }

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

    @Getter
    @Setter
    private static class Test {
        private Person person;

        private List<Person> personList;
    }

    @Getter
    @Setter
    private static class Person {
        private String name;

        private Address address;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    private static class Address {
        private String street;
    }
}

// Find the class type in run time
abstract class Human<T> {
    Class<T> object;

    public Human() {
        this.object = (Class<T>) ((ParameterizedType) getClass()
                .getGenericSuperclass()).getActualTypeArguments()[0];
        System.out.println(this.object);
    }
}

class Student extends Human<Student> {

}
