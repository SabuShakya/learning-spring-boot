package com.sabu.java8practice.functionalInterface;

import java.util.function.BiFunction;
import java.util.function.Function;

public class _Function {

    public static void main(String[] args) {
        int increment = increment(1);
        System.out.println(increment);

        /* Functional interfaces*/
        Integer increment1 = incrementByOne.apply(1);
        System.out.println("increment by functional: " + increment1);

        int multiplied = multiplyByTen.apply(increment1);
        System.out.println(multiplied);

        /* Chaining Functions with andThen*/
        Function<Integer, Integer> integerFunction = incrementByOne.andThen(multiplyByTen);
        System.out.println(integerFunction.apply(1));

        Integer sumOfTwoNums = addTwoNumbers.apply(555, 898);
        System.out.println("sum : " + sumOfTwoNums);
    }

    static int increment(int number) {
        return number + 1;
    }

    static Function<Integer, Integer> incrementByOne = number -> number + 1;

    static Function<Integer, Integer> multiplyByTen = number -> number * 10;

    static BiFunction<Integer, Integer, Integer> addTwoNumbers = (num1, num2) -> num1 + num2;
}
