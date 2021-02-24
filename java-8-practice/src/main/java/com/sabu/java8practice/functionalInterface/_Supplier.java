package com.sabu.java8practice.functionalInterface;

import java.util.Collections;
import java.util.List;
import java.util.function.Supplier;

public class _Supplier {

    public static void main(String[] args) {
        /* Normal function that returns a value */
        System.out.println(getDBConnectionUrl());

        System.out.println("Supplier in Action");
        System.out.println(getDBConnectionSupplier.get());
        System.out.println("List of Strings from supplier" + getDBConnectionSupplierList.get());
    }

    static String getDBConnectionUrl() {
        return "jdb://localhost:3306/users";
    }

    static Supplier<String> getDBConnectionSupplier = () -> "jdb://localhost:3306/users";


    static Supplier<List<String>> getDBConnectionSupplierList = () -> Collections.singletonList("jdb://localhost:3306/users");
}
