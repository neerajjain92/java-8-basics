package com.java8.basics.stream;

import com.google.common.collect.ImmutableList;

import java.util.*;
import java.util.function.Function;

import static java.util.stream.Collectors.*;
import static com.java8.basics.stream.Util.*;

/**
 * Collector is the class and interface as well
 */
public class Java8Collectors {

    private static List<String> givenList;

    static {
        givenList = Arrays.asList("a", "bb", "ccc", "dd", "ccc");
    }

    public static void main(String[] args) {

        log("Collectors.toList");
        collectorsToList();

        log("Collectors.toSet");
        collectorsToSet();

        // toSet() and toList() doesn't guarantee a particular implementation
        // of List or Set so if we need a specific implementation
        // we can use Collectors.toCollection();

        log("Collectors.toCollection(SpecificImplementation::new)");
        collectorsToCollection();

        // 3.4. Collectors.toMap()
        // ToMap collector can be used to collect Stream elements into a Map
        // instance. To do this, you need to provide two functions:
        // keyMapper
        // valueMapper
        // Note if the key is duplicate toMap throws DuplicateKey Exception
        // To avoid it we can use toMap(Function keyMapper,Function valueMapper,BinaryOperator mergeFunction)

        // Important: mergeFunction operates on 2 values associated with the same key
        log("Collectors.toMap");
        collectorsToMap();

        log("Collectors.collectingAndThen()");
        collectorsCollectingAndThen();

        // We can join the elements of Stream using joining
        log("Collectors.joining()");
        collectorsJoining();

        log("Collectors.counting");
        collectorsCounting();

        log("Collectors.maxBy()/minBy()");
        collectorsMaxAndMin();

        //GroupingBy collector is used for grouping objects by some property and storing results in a Map instance.
        log("Collectors.groupingBy(classifier, collector)");
        collectorsGroupingBy();

        // Special case of grouping by
        // it expects a PredicateInstance and collect Stream into a Map
        // with keys as Boolean[true, false]
        // true where predicate passed
        // false where predicate failed
        log("Collectors.partitioningBy()");
        collectorsPartitioningBy();
    }

    private static void collectorsPartitioningBy() {
        Map<Boolean, List<String>> result = givenList.stream().collect(partitioningBy(s -> s.length() > 2));
        System.out.println(result);
    }

    private static void collectorsGroupingBy() {
        Map<Integer, Set<String>> groupedResult = givenList.stream().collect(groupingBy(String::length, toSet()));
        System.out.println(groupedResult);
    }

    private static void collectorsMaxAndMin() {
        Optional<String> result = givenList.stream().collect(maxBy(Comparator.naturalOrder()));
        System.out.println("Maximum is " + result);
        result = givenList.stream().collect(minBy(Comparator.naturalOrder()));
        System.out.println("Minimum is " + result);
    }

    private static void collectorsCounting() {
        Long result = givenList.stream().collect(counting());
        System.out.println(result);
        result = givenList.stream().count();
        System.out.println(result);
    }

    private static void collectorsJoining() {

        System.out.println(">>>>Without Space<<<");
        String result = givenList.stream().collect(joining());
        System.out.println(result);

        System.out.println(">>>>With Space<<<");
        result = givenList.stream().collect(joining(" "));
        System.out.println(result);

        System.out.println(">>>> With Custom Separator, Prefix and Postfixes");
        result = givenList.stream().collect(joining(",", "PRE [ ", " ] POST"));
        System.out.println(result);
    }

    private static void collectorsCollectingAndThen() {
        List<String> result = givenList.stream().collect(collectingAndThen(toList(), ImmutableList::copyOf));
        System.out.println("Immutable list " + result);
    }

    private static void collectorsToMap() {
        Map<String, Integer> resultMap = givenList.stream()
                .collect(toMap(Function.identity(), String::length, (value1, value2) -> {
                    System.out.println("Duplicate key found");
                    return value1;
                }));
        System.out.println(resultMap);

        // Short hand for mergeFunction

        // Map<String, Integer> result = givenList.stream()
        //      .collect(toMap(Function.identity(), String::length, (i1, i2) -> i1));
    }

    private static void collectorsToCollection() {
        List<String> result = givenList.stream().collect(toCollection(LinkedList::new));
        System.out.println(result);
    }

    private static void collectorsToSet() {
        Set<String> result = givenList.stream().collect(toSet());
        System.out.println(result);
    }

    private static void collectorsToList() {
        List<String> result = givenList.stream().collect(toList());
        System.out.println(result);
    }
}
