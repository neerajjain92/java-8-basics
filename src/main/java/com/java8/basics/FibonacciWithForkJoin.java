package com.java8.basics;

import java.util.concurrent.RecursiveTask;

/**
 * @author neeraj on 2019-06-25
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class FibonacciWithForkJoin extends RecursiveTask<Integer> {

    int n;

    public FibonacciWithForkJoin(int n) {
        this.n = n;
    }

    @Override
    protected Integer compute() {
        if (n <= 1)
            return n;
        FibonacciWithForkJoin f1 = new FibonacciWithForkJoin(n - 1);
        f1.fork();
        FibonacciWithForkJoin f2 = new FibonacciWithForkJoin(n - 2);
        f2.fork();
        return f1.join() + f2.join();
    }

    public static void main(String[] args) {
        FibonacciWithForkJoin fibonacci = new FibonacciWithForkJoin(10);
        System.out.println(fibonacci.compute());
    }
}
