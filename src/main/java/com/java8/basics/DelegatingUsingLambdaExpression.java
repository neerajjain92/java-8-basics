package com.java8.basics;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * https://www.youtube.com/watch?v=WN9kgdSVhDo
 * Lazy initialization of Object, delegating it. until actually necessary.
 * ==> Using supplier.
 *
 * @author neeraj on 2019-06-23
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class DelegatingUsingLambdaExpression {

    public static int compute(int n) {
        System.out.println("called......");
        return n * 2;
    }

    public static void main(String[] args) {
        int x = 14;

        var temp = new Lazy<>(() -> compute(x));

        if ((x > 5) && temp.get() > 7) {
            System.out.println("Path 1..... with ==> " + temp.get());
        } else {
            System.out.println("Path 2.....");
        }

        Thread thread = new Thread( () -> System.out.println("Hello"));
    }
}

class Lazy<T> {
    private T instance;
    private Supplier<T> supplier;

    public Lazy(Supplier<T> supplier) {
        this.supplier = supplier;
    }

    public T get() {
        if (instance == null) {
            instance = supplier.get();
            supplier = null;
        }
        return instance;
    }
}
