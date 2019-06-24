package com.java8.basics.functional_interface;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.IntFunction;

/**
 * @author neeraj on 2019-06-24
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class ComposingFunctions {

    public static void main(String[] args) {
        Function<Integer, String> integerToString = Objects::toString;

        Function<String, String> applyQuotes = val -> "'" + val + "'";

        Function<Integer, String> applyQuotes_To_IntegerToString = applyQuotes.compose(integerToString);

        System.out.println(applyQuotes_To_IntegerToString.apply(9123));


    }

}
