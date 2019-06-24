package com.java8.basics;

import java.awt.*;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * Decorator Design Pattern with Lambda Expression.
 *
 * @author neeraj on 2019-06-23
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class DecoratingUsingLambdaExpression {

    public static void printIt(int n, String msg, Function<Integer, Integer> func) {
        System.out.println(n + " " + msg + " : " + func.apply(n));
    }

    public static void main(String[] args) {
        Function<Integer, Integer> increment = e -> e + 1;

        printIt(5, " :: increment", increment);
        printIt(10, " :: increment", increment);

        Function<Integer, Integer> doubled = e -> e * 2;
        printIt(10, " :: doubled", doubled);
        printIt(40500, " :: doubled", doubled);

        printIt(5, " :: increment first and then double it", increment.andThen(doubled));

        // Decorator Pattern
        shootIt(new Camera());
        shootIt(new Camera(Color::brighter));
        shootIt(new Camera(Color::darker));

        // Composing different Decorators
        shootIt(new Camera(Color::brighter, Color::darker));
    }

    public static void shootIt(Camera camera) {
        System.out.println(camera.snap(new Color(125, 125, 125)));
    }
}

class Camera {
    private Function<Color, Color> filter;

    // Note : Please do read the comments below, they are important.!!!
    public Camera(Function<Color, Color>... filters) {
//        filter = input -> input;
//        for (Function<Color, Color> aFilter : filters) {
//             filter = filter.andThen(aFilter);
//        }
        // Commenting the above code to refactor it in a Functional Style

        // And what we are really doing here is an operation called reduce operation.

//        filter = Stream.of(filters)
//                .reduce(input -> input, (aFilter, result) -> result.andThen(aFilter));

        // Refactoring the above functional style again

        filter = Stream.of(filters)
                .reduce(Function.identity(), Function::andThen);

    }

    public Color snap(Color input) {
        return filter.apply(input);
    }
}
