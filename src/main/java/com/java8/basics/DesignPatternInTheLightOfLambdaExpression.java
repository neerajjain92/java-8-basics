package com.java8.basics;

import java.util.List;
import java.util.function.Predicate;

/**
 * https://www.youtube.com/watch?v=WN9kgdSVhDo
 * Predicates came to safety for Strategy Design Pattern.
 *
 * @author neeraj on 2019-06-23
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class DesignPatternInTheLightOfLambdaExpression {

    public static int calculateSum(List<Integer> values, Predicate<Integer> selector) {
        return values
                .stream()
                .filter(selector)
                .mapToInt(e -> e)
                .sum();
    }

    public static void main(String[] args) {
        var values = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);
        System.out.println("Total Sum " + calculateSum(values, e -> true));
        System.out.println("Even Sum " + calculateSum(values, e -> e % 2 == 0));
        System.out.println("Odd Sum " + calculateSum(values, e -> e % 2 != 0));
    }
}
