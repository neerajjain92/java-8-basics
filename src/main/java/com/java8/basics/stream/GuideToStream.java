package com.java8.basics.stream;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class GuideToStream {

    public static void main(String[] args) {

        // Get Employee Stream
        log("Get Employee Stream using Stream's builder method");
        printStream(getEmployeeStream());

        log("Employees Greater than 28 years");
        printStream(getEmployeeStream().filter(employee -> employee.age > 28));

        log("Different ways to use Stream's.collect() Method");
        log("Stream.collect() using Supplier, Accumulator and Combiner");
        collectType1();

        log("Stream.collect() using Collector");
        collectType2();

    }

    /**
     * Stream.collect() using Supplier, Accumulator and Combiner
     * We are using 3 arguments in this collect example,
     * Supplier, Accumulator and Combiner
     * <p>
     * Supplier: It creates a new result container which will be populated by accumulator and combiner and finally
     * it will be returned by collect() method.
     * <p>
     * Accumulator: It incorporates additional element into the result.
     * <p>
     * Combiner:  It combines two values that must be compatible with accumulator. Combiner works in parallel processing.
     */
    private static void collectType1() {
        List<String> list = Arrays.asList("Mukesh", "Vishal", "Amar");
        String result = list.parallelStream().collect(StringBuilder::new,
                (response, element) -> response.append(" ").append(element),
                (response1, response2) -> response1.append(",").append(response2.toString())).toString();

        System.out.println(result);


        // Note: If we use list.stream() instead of list.parallelStream() then output will be
        //  Mukesh Vishal Amar instead of  Mukesh, Vishal, Amar
        // because Nothing to combine
    }


    /**
     * Stream.collect() using Collector
     */
    private static void collectType2() {
        List<Integer> list = Arrays.asList(23, 43, 12, 25);

        //A state object for collecting statistics such as count, min, max, sum, and average
        IntSummaryStatistics stats = list.stream().collect(Collectors.summarizingInt(i -> i + i));

        System.out.println(stats.getSum());
    }

    static void log(String message) {
        System.out.println("=================>>>" + message + "<<<================");
    }

    static void printStream(Stream stream) {
        stream.forEach(System.out::println);
    }

    static Stream<Employee> getEmployeeStream() {
        Stream.Builder<Employee> employeeStreamBuilder = Stream.builder();

        employeeStreamBuilder.accept(new Employee(1, "Jitu", "Saini", 27));
        employeeStreamBuilder.accept(new Employee(2, "Neeraj", "Jain", 26));
        employeeStreamBuilder.accept(new Employee(3, "Sagar", "Batra", 29));
        return employeeStreamBuilder.build();
    }
}

class Employee {
    int empId;
    String firstName;
    String lastName;
    int age;

    public Employee(int empId, String firstName, String lastName, int age) {
        this.empId = empId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.age = age;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "empId=" + empId +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", age=" + age +
                '}';
    }
}
