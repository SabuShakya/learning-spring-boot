package com.sabu.java8practice.functionalInterface;

import java.util.function.Predicate;

public class _Predicate {

    public static void main(String[] args) {

        System.out.println("Normal Function : ");
        System.out.println(isPhoneNumberValid("6565655555"));
        System.out.println(isPhoneNumberValid("9800656555"));
        System.out.println(isPhoneNumberValid("980065655500"));

        System.out.println("Using Predicate");
        System.out.println(isPhoneNumberValidPredicate.test("6565655555"));
        System.out.println(isPhoneNumberValidPredicate.test("9800656555"));
        System.out.println(isPhoneNumberValidPredicate.test("980065655500"));

        /* Chaining predicate */
        System.out.println("Is Phone Number valid and contains 3 " +
                isPhoneNumberValidPredicate.and(containsNumber3).test("9800663222")); // all have to be true
        System.out.println("Is Phone Number valid and contains 3 " +
                isPhoneNumberValidPredicate.or(containsNumber3).test("980066322222")); // either case true

    }

    static boolean isPhoneNumberValid(String phoneNumber) {
        return phoneNumber.startsWith("98") && phoneNumber.length() == 10;
    }

    static Predicate<String> isPhoneNumberValidPredicate = phoneNumber ->
            phoneNumber.startsWith("98") && phoneNumber.length() == 10;

    static Predicate<String> containsNumber3 = phoneNumber -> phoneNumber.contains("3");
}
