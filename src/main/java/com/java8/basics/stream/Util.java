package com.java8.basics.stream;

import java.util.stream.Stream;

public class Util {

    public static void log(String message) {
        System.out.println("=================>>>" + message + "<<<================");
    }

    public static Stream<Employee> getEmployeeStream() {
        Stream.Builder<Employee> employeeStreamBuilder = Stream.builder();

        employeeStreamBuilder.accept(new Employee(1, "Jitu", "Saini", 27));
        employeeStreamBuilder.accept(new Employee(2, "Neeraj", "Jain", 26));
        employeeStreamBuilder.accept(new Employee(3, "Sagar", "Batra", 29));
        return employeeStreamBuilder.build();
    }

    static void printStream(Stream stream) {
        stream.forEach(System.out::println);
    }
}
