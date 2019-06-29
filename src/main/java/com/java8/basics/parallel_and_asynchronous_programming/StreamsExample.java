package com.java8.basics.parallel_and_asynchronous_programming;

import java.util.List;
import java.util.stream.Stream;

/**
 * @author neeraj on 2019-06-26
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class StreamsExample {

    public static int transform(int data) {
        System.out.println("transform: " + data + " -- " + Thread.currentThread());
        sleep(1000);
        return data * 1;
    }

    public static void printIt(int data) {
        System.out.println("print: " + data + " -- " + Thread.currentThread());
    }


    public static void main(String[] args) {
        List<Integer> sampleData = List.of(1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25);

//        System.out.println("Running Sequential Stream");
//        sampleData.stream().map(data -> transform(data)).forEach(e -> {});

        System.out.println("===========================Running Parallel Stream===========================");
        sampleData.parallelStream().map(data -> transform(data)).forEach(e -> printIt(e));

//        // If I used ordered version of ForEach then we will get the response in order although the transformation
//        // happens concurrently
//        System.out.println("===========================Using ordered version of ForEach===========================");
//        sampleData.parallelStream().map(data -> transform(data)).forEachOrdered(e -> printIt(e));
//
//        // If the Stream maintains ordering then only forEachOrdered maintains ordering else not, so if we use
//        // Set instead of list in the above example forEachOrdered might not return data in ordered way
//        System.out.println("===========================For Each Ordered with Set============================");
//        Set<Integer> sample = Set.of(1,2,3,4,5,6);
//        sample.parallelStream().map(data -> transform(data)).forEachOrdered(e -> printIt(e));


        // If we are the source of stream then we can simply call parallelStream() else we have to call parallel() on
        // the stream supplied by other supplier
//        useThisStream(sampleData.stream());

    }

    private static void useThisStream(Stream<Integer> stream) {
        System.out.println("Using the parallel() method on the stream provided by someone else");
        stream
                .parallel()
                .map(data -> transform(data))
                .forEach(System.out::println);
    }

    private static void sleep(int ms) {
        try {
            Thread.sleep(ms);
        } catch (InterruptedException ex) {
            ex.printStackTrace();
        }
    }
}
