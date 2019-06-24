package com.java8.basics.functional_interface;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * @author neeraj on 2019-06-24
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class SupplierExamples {

    public static void main(String[] args) {
        Supplier<Double> doubleSupplier = () -> {
            try {
                System.out.println("...Sleeping for 1 Second...");
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return 9d;
        };

        System.out.println("Squared Value is " + squareLazily(doubleSupplier));
    }

    /**
     * For instance, let’s define a function that squares a `double value. It will receive not a value itself,
     * but a Supplier of this value:
     */
    public static double squareLazily(Supplier<Double> supplier) {
        return Math.pow(supplier.get(), 2);
    }
}
