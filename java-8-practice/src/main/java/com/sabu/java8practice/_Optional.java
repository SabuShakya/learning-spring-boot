package com.sabu.java8practice;

import java.util.Optional;

public class _Optional {

    public static void main(String[] args) {
        Optional<String> nonEmptyGender = Optional.of("male");
        Optional<String> emptyGender = Optional.empty();

        System.out.println("Non-Empty Optional::" + nonEmptyGender.map(String::toUpperCase));
        System.out.println("Empty Optional::" + emptyGender.map(String::toUpperCase));

        Optional<Optional<String>> nonEmptyOptionalGender = Optional.of(Optional.of("female"));
        System.out.println("Optional Value :: " + nonEmptyOptionalGender);
        System.out.println("Optional.map  :: " + nonEmptyOptionalGender.map(gender -> gender.map(String::toUpperCase)));
        System.out.println("Optional.flatmap  :: " + nonEmptyOptionalGender.flatMap(gender -> gender.map(String::toUpperCase)));

        if (nonEmptyGender.isPresent())
            System.out.println("Value is present.");
        else
            System.out.println("Value not present.");

        nonEmptyGender.ifPresent(s -> System.out.println("Performing action on non empty:" + s));

        emptyGender.ifPresent(s -> System.out.println("Wont be printed as empty value."));


    }
}
