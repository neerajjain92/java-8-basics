package com.java8.basics.functional_interface;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntFunction;
import java.util.function.ToLongFunction;

/**
 * https://www.baeldung.com/java-8-functional-interfaces
 *
 * @author neeraj on 2019-06-24
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class PrimitiveFunctionSpecialization {

    public static void main(String[] args) {

        /**
         * Since a primitive type can’t be a generic type argument, there are versions of the Function interface for most
         * used primitive types double, int, long, and their combinations in argument and return types:
         *
         * [1] IntFunction, LongFunction, DoubleFunction: arguments are of specified type, return type is parameterized
         * [2] ToIntFunction, ToLongFunction, ToDoubleFunction: return type is of specified type, arguments are parameterized
         * [3] DoubleToIntFunction, DoubleToLongFunction, IntToDoubleFunction, IntToLongFunction, LongToIntFunction, LongToDoubleFunction — having both argument and return type defined as primitive types, as specified by their names
         */

        IntFunction lengthOfDigit = i -> String.valueOf(i).length();
        System.out.println("Length of digit is " + lengthOfDigit.apply(982392187));

        ToLongFunction<String> stringToLong = str -> Long.parseLong(str);
        System.out.println("String to Long is " + stringToLong.applyAsLong("12345678987654321"));

        DoubleToIntFunction doubleToIntFunction = data -> (int) data;

        System.out.println("Double to Int is " + doubleToIntFunction.applyAsInt(3.14));

        // There is no out-of-the-box functional interface for, say, a function that takes a short and returns a byte,
        // but nothing stops you from writing your own:
        short[] array = {(short) 1, (short) 2, (short) 3};
        byte[] byteArr = transformArray(array, s -> (byte) (s * 2));

        for (byte b : byteArr) {
            System.out.println(b);
        }
    }

    public static byte[] transformArray(short[] array, ShortToByteFunction shortToByteFunction) {
        byte[] transformedArray = new byte[array.length];
        for (int i = 0; i < array.length; i++) {
            transformedArray[i] = shortToByteFunction.applyAsByte(array[i]);
        }
        return transformedArray;
    }
}

@FunctionalInterface
interface ShortToByteFunction {
    byte applyAsByte(Short s);
}
