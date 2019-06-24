package com.java8.basics.functional_interface;

import java.util.HashMap;
import java.util.Map;

/**
 * @author neeraj on 2019-06-24
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class BiFunctionalInterface {

    public static void main(String[] args) {

        Map<String, Integer> salaries = new HashMap<>();
        salaries.put("John", 40000);
        salaries.put("Freddy", 30000);
        salaries.put("Samuel", 50000);

        salaries.replaceAll((name, oldValue) -> name.equals("Freddy") ? oldValue : oldValue + 15000);

        System.out.println(salaries);
    }
}
