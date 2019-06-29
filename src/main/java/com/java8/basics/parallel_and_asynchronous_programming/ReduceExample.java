package com.java8.basics.parallel_and_asynchronous_programming;

import java.util.List;
import java.util.concurrent.ForkJoinPool;

/**
 * @author neeraj on 2019-06-27
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class ReduceExample {

    public static void main(String[] args) {
        List<Integer> sampleData = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20);

        System.out.println(sampleData.parallelStream().reduce(0, (total, currentElement) -> add(total, currentElement)));

        // reduce does not take a initial value as the first parameter but instead a identity value

        // What is a identity value, that if this value is being applied to another value with specific operator will not change the result.
        // So Identity value for addition (+) of Integers is "0";
        // and Identity value for multiplication (*) of Integers is "1"; and so on......

        System.out.println("=============== Let's check the default number of threads===============");
        System.out.println("Total Numbers of cores in my machine :" + Runtime.getRuntime().availableProcessors());
        System.out.println(ForkJoinPool.commonPool());
    }

    private static Integer add(Integer total, Integer currentElement) {
        int result = total + currentElement;
        System.out.println("total = " + total + " currentElement = " + currentElement + " result = " + result + " -- " + Thread.currentThread());
        return result;
    }
}
