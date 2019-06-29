package com.java8.basics.parallel_and_asynchronous_programming;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.stream.IntStream;

/**
 * @author neeraj on 2019-06-28
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class NumberOfThreadsExample {

    static int MAX = 1500;

    public static double compute(double number) {
        double result = 0;
        for (int i = 0; i < MAX; i++) {
            for (int j = 0; j < MAX; j++) {
                result += Math.sqrt(i * j);
            }
        }
        return result;
    }


    public static void main(String[] args) {
        System.out.println("=============== Let's check the default number of threads===============");
        System.out.println("Total Numbers of cores in my machine :" + Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool());
        double result = IntStream.range(0, MAX)
                .parallel()
                .mapToDouble(NumberOfThreadsExample::compute)
                .sum();
        System.out.println(result);


        System.out.println("========Find First Lady whose age is greater than 30=============");

        List<Person> allPersons = List.of(
                new Person("Sara", GENDER.FEMALE, 20),
                new Person("Sara", GENDER.FEMALE, 22),
                new Person("Bob", GENDER.MALE, 20),
                new Person("Paula", GENDER.FEMALE, 32),
                new Person("Paul", GENDER.MALE, 32),
                new Person("Jack", GENDER.MALE, 2),
                new Person("Jack", GENDER.MALE, 72),
                new Person("Jill", GENDER.FEMALE, 42)
        );

        System.out.println(
                allPersons
                        .parallelStream()
                        .filter(person -> person.age > 30)
                        .filter(person -> person.gender == GENDER.FEMALE)
                        .map(person -> person.getName())
                        .findFirst()
                        .orElse("Not Found"));

        System.out.println("========Find any Lady whose age is greater than 30=============");
        System.out.println(
                allPersons
                        .parallelStream()
                        .filter(person -> person.getAge() > 30)
                        .filter(person -> person.getGender() == GENDER.FEMALE)
                        .map(person -> person.getName())
                        .findAny()
                        .orElse("Not Found"));

        // Since the Streams are lazy evaluated, so if we run this in sequential stream
        // We know getAge will be calculated on for Sara, Sara, Bob and Paula, as soon as we find Paula
        // program exists.
        // If we use parallel streams we might get more comparision to do because of parallel nature.

        System.out.println("========Find any Lady whose age is greater than 30 using sequential streams=============");
        System.out.println(
                allPersons
                        .stream()
                        .filter(person -> person.getAge() > 30)
                        .filter(person -> person.getGender() == GENDER.FEMALE)
                        .map(person -> person.getName())
                        .findAny()
                        .orElse("Not Found"));
    }
}

enum GENDER {
    MALE, FEMALE;
}

class Person {
    String name;
    GENDER gender;
    int age;

    public String getName() {
        return name;
    }

    public GENDER getGender() {
        return gender;
    }

    public int getAge() {
        System.out.println(" Age for " + name);
        return age;
    }

    public Person(String name, GENDER gender, int age) {
        this.name = name;
        this.gender = gender;
        this.age = age;
    }
}
