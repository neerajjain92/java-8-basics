package com.java8.basics.stream;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

import static com.java8.basics.stream.Util.log;

/**
 * java.util.function.Function<T,R>
 */
public class FunctionInterfaceInternalExample {

    static List<Employee> employees;

    static {
        employees = Arrays.asList(new Employee(45, "Tom", "Jones"),
                new Employee(25, "Harry", "Major"),
                new Employee(65, "Ethan", "Hardy"),
                new Employee(15, "Nancy ", "Smith"),
                new Employee(29, "Deborah", "Sprightly"));
    }

    public static void main(String[] args) {

        // Apply Method in Function Interface
        log("Usage of apply() method of Function");
        applyMethodExample();

        // Default Compose Method in Function Interface
        log("Usage of default method compose() of Function");
        composeMethodExample();

        // Default andThen Method in Function Interface
        log("Usage of default method andThen() of Function");
        andThenExample();
    }

    /**
     * default <V> Function<V,R> compose(Function<? super V,? extends T> before)
     * Returns a composed function that first applies the before function to its input,
     * and then applies this function to the result.
     * If evaluation of either function throws an exception, it is relayed to the caller of the composed function.
     */
    private static void composeMethodExample() {
        Function<Employee, String> currentFunction = (Employee employee) -> {
            return employee.getFullName();
        };

        Function<Employee, Employee> beforeFunction = (Employee employee) -> {
            int index = employee.getFullName().indexOf(" ");
            String firstName = employee.getFullName().substring(0, index);
            employee.fullName = firstName;
            return employee;
        };

        System.out.println(convertEmpListToNameList(currentFunction.compose(beforeFunction)));
    }

    /**
     * default <V> Function<T,V> andThen(Function<? super R,? extends V> after)
     * Returns a composed function that first applies this function to its input,
     * and then applies the after function to the result. If evaluation of either function throws an exception,
     * it is relayed to the caller of the composed function.
     */
    private static void andThenExample() {
        Function<Employee, String> currentFunction = (Employee employee) -> {
            return employee.getFullName();
        };
        // This will return the initials
        Function<String, String> afterFunction = (String s) -> s.substring(0, 1);

        System.out.println(convertEmpListToNameList(currentFunction.andThen(afterFunction)));
    }

    /**
     * R apply(T t)
     * Applies this function to the given argument.
     */
    private static void applyMethodExample() {
        Function<Employee, String> functionEmpToString = (Employee employee) -> {
            return employee.getFullName();
        };
        System.out.println(convertEmpListToNameList(functionEmpToString));
    }

    private static List<String> convertEmpListToNameList(Function<Employee, String> function) {
        List<String> empNamesList = new ArrayList<>();

        for (int i = 0; i < employees.size(); i++) {
            empNamesList.add(function.apply(employees.get(i)));
        }
        return empNamesList;
    }
}
