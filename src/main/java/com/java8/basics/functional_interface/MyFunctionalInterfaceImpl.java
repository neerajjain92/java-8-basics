package com.java8.basics.functional_interface;

public class MyFunctionalInterfaceImpl {

    public static void main(String[] args) {
        MyFunctionalInterface functionalInterface = (String guestName) -> {
            System.out.println("Welcome " + guestName);
        };

        functionalInterface.greet("Neeraj Jain");

        // How Lambda should actually be used

        showHowLambdaCanBeUsed((String param) -> {
            System.out.println("Hello ");
        }, "Neeraj");

        showHowLambdaCanBeUsed((String param) -> {
            System.out.println("Welcome to our Hotel, Mr.");
        }, "NJ");
    }

    private static void showHowLambdaCanBeUsed(MyFunctionalInterface functionalInterface, String guestName) {
        functionalInterface.greet(guestName);
    }
}
