package com.java8.basics.stream;

import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static com.java8.basics.stream.Util.*;
import static java.util.stream.Collectors.toList;

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

        // All Collectors are being covered in Java8Collectors class

        // flatMap first apply a function to your elements, and then flatten it.
        // Input : [ [1,2,3],[4,5,6],[7,8,9] ]
        // Output: [ 1,2,3,4,5,6,7,8,9 ].
        log("Stream flatMap() ");
        flatMapSample();

        //Java 8 Streams API – creating infinite streams with iterate and generate methods
        log("Java 8 Streams API – creating infinite streams with iterate and generate methods");
        infinteStreamExample();

        // Lazy Evaluation in Stream
        log("Lazy Evaluation in Stream");
        lazyEvaluationExample();

        // Comparison Based Stream Operations
        log("[Comparison Based Stream Operations]");
        comparisionBasedStreamOperations();

        // Stream Specializations
        log("Stream Specializations IntStream, LongStream, and DoubleStream etc.");
        streamSpecializationExample();
    }

    /**
     * // Boxed is needed to convert IntStream to the Stream<Integer> so that we can now collect it.
     * https://stackoverflow.com/questions/23674624/how-do-i-convert-a-java-8-intstream-to-a-list
     */
    private static void streamSpecializationExample() {
        System.out.println("List of Id's with mapToInt" + getEmployeeStream().mapToInt(Employee::getEmpId).boxed().collect(toList()));

        // If we don't use boxed
        System.out.println("List of Age of Employees " + getEmployeeStream().mapToInt(Employee::getAge).mapToObj(age -> age).collect(toList()));
    }

    private static void comparisionBasedStreamOperations() {
        log("Min Max Example");
        System.out.println("Min " + getEmployeeStream().min(Comparator.comparing(Employee::getAge)));
        System.out.println("Max " + getEmployeeStream().max(Comparator.comparing(Employee::getAge)));

        log("Sorted Example with Comparator's comparing ");
        getEmployeeStream().sorted(Comparator.comparing(Employee::getFullName)).forEach(System.out::println);

        log("Distinct Example");
        List<Integer> empIds = Arrays.asList(1, 2, 3, 5, 2, 1, 6, 3, 6, 7, 9);
        System.out.println("Distinct of " + empIds + " is below");
        System.out.println(empIds.stream().distinct().collect(toList()));

        log("allMatch, anyMatch, and noneMatch");
        boolean allEven = empIds.stream().allMatch(getEvenPredicate());
        boolean anyEven = empIds.stream().anyMatch(getEvenPredicate());
        boolean noneMatch = empIds.stream().noneMatch(getEvenPredicate());

        System.out.println(empIds);
        System.out.println("All Even : " + allEven + " Any Even : " + anyEven + " None Even :" + noneMatch);
    }

    private static Predicate getEvenPredicate() {
        Predicate<Integer> evenPredicate = id -> (id % 2) == 0;
        return evenPredicate;
    }

    /**
     * Employee 1 Age 27
     * Employee 2 Age 26
     * Employee 3 Age 29
     * In this Example whenever after intermediate operation is applied on Employee 1 and all predicates are met
     * then findOne will simply return the first element without even processing the Employee 2 and Employee 3
     * <p>
     * <p>
     * Note: Processing streams lazily allows avoiding examining all the data when that’s not necessary.
     * This behavior becomes even more important when the input stream is infinite and not just very large.
     */
    private static void lazyEvaluationExample() {
        Integer[] empIds = {1, 2, 3, 4};
        Stream<Employee> employeeStream = getEmployeeStream();

        System.out.println(Stream.of(empIds).map(empId -> findById(employeeStream, empId)).
                filter(employee -> employee.age > 26).findFirst().orElse(null));
    }

    private static void infinteStreamExample() {
        System.out.println("With Iterate method");
        List<Integer> infiniteStreamValues = Stream.iterate(2, i -> i * 2).skip(5).limit(10).collect(toList());
        System.out.println(infiniteStreamValues);

        // Stream.generate doesn't have any initial seed unlike iterate
        System.out.println("With Generate Method");
        System.out.println(Stream.generate(Math::random).limit(5).collect(toList()));
    }

    private static void flatMapSample() {
        List<List<String>> namesNested = Arrays.asList(
                Arrays.asList("Jeff", "Bezos"),
                Arrays.asList("Bill", "Gates"),
                Arrays.asList("Mark", "Zuckerberg"));

        List<String> members = namesNested.stream().flatMap(Collection::stream).collect(toList());
        System.out.println(members);
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

    public static Employee findById(Stream<Employee> stream, int empId) {
        return stream.filter(employee -> employee.empId == empId).findFirst().orElse(null);
    }
}

