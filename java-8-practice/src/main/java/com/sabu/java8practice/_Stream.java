package com.sabu.java8practice;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class _Stream {

    public static void main(String[] args) {
        List<String> nameList = Arrays.asList("Sabu", "Dua", "Martin", "Troy");
        nameList.stream().forEach(name -> System.out.println("name: " + name));

        List<Person> personList = nameList.stream()
                .map(Person::new)
                .collect(Collectors.toList());
        System.out.println("Original List :: " + personList);

        List<Person> personList1 = personList.stream()
                .filter(person -> !person.getName().startsWith("S"))
                .collect(Collectors.toList());

        personList.stream()
                .map(Person::getName)
                .collect(Collectors.toSet())
                .forEach(System.out::println);

        personList.stream()
                .map(Person::getName)
//                .mapToInt(name-> name.length() ) this can be replaced simply with method reference.
                .mapToInt(String::length)
                .forEach(System.out::println);

        System.out.println("Filtered list " + personList1);

        personList.stream()
                .allMatch(person -> person.getName().contains("a"));
    }
}
