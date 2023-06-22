package com.sabu.java8practice.functionalInterface;

import com.sun.org.apache.xpath.internal.operations.Bool;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class _Consumer {

    public static void main(String[] args) {
        /* Normal function */
        greetCustomer(new Customer("Mona", "9874455122"));

        /* Consumer Functional Interface */
        greetCustomerConsumer.accept(new Customer("Mona", "9874455122"));

        /* BiConsumer Functional Interface */
        greetCustomer2.accept(new Customer("Sabu", "9999999999"), false);
    }

    static Consumer<Customer> greetCustomerConsumer = customer ->
            System.out.println("Hello " + customer.customerName + ", thanks for registering phone number "
                    + customer.customerPhoneNumber);

    static BiConsumer<Customer, Boolean> greetCustomer2 = ((customer, showMobileNumber) ->
            System.out.println("Hello " + customer.customerName + ", thanks for registering phone number "
                    + (showMobileNumber ? customer.customerPhoneNumber : "**********")));

    static void greetCustomer(Customer customer) {
        System.out.println("Hello " + customer.customerName + ", thanks for registering phone number "
                + customer.customerPhoneNumber);
    }

    static class Customer {
        private final String customerName;

        private final String customerPhoneNumber;

        Customer(String customerName, String customerPhoneNumber) {
            this.customerName = customerName;
            this.customerPhoneNumber = customerPhoneNumber;
        }
    }
}
