package com.java8.basics;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.RandomStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * https://dzone.com/articles/thread-pool-self-induced-deadlocks
 *
 * @author neeraj on 2019-06-26
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class DeadLockExample {

    public static void main(String[] args) {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        WoodLogging woodLogging = new WoodLogging(new RandomStringUtils());
        try (Forest forest = new Forest(pool, woodLogging)) {
            // If we change the number of ChillOutWorkers from 0 to 1, system quite often fails due to deadlock.
            forest.cutTrees(10_000, 10, 5);
        } catch (TimeoutException | InterruptedException e) {
            System.out.println("Working for too long " + e.getMessage());
        }


        // Thread Pool Induced Deadlock
        ExecutorService inducedDeadLockPool = Executors.newSingleThreadExecutor();
        inducedDeadLockPool.submit(() -> {
            System.out.println("First");
            // Here internal task is again submitting task to Pool
//            inducedDeadLockPool.submit(() -> System.out.println("Second"));

            // Now if we make this submission of internal task to pool as blocking, it becomes inducedDeadLockPool
            try {
                inducedDeadLockPool.submit(() -> System.out.println("Second")).get();
            } catch (InterruptedException e) {
                e.printStackTrace();
            } catch (ExecutionException e) {
                e.printStackTrace();
            }
            System.out.println("Third");
        });
    }
}

@RequiredArgsConstructor
class Forest implements AutoCloseable {

    private static final Logger log = LoggerFactory.getLogger(Forest.class);
    private final ExecutorService pool;
    private final WoodLogging woodLogging;

    void cutTrees(int howManyTrees, int carefulLumberJacks, int chillOutWorkers) throws TimeoutException, InterruptedException {
        CountDownLatch countDownLatch = new CountDownLatch(howManyTrees);
        List<LumberJack> lumberJacks = new ArrayList<>();

        lumberJacks.addAll(generateLumberJacks(carefulLumberJacks, woodLogging::careFulWorker));
        lumberJacks.addAll(generateLumberJacks(chillOutWorkers, woodLogging::chillOutWorker));

        IntStream
                .range(0, howManyTrees)
                .forEach(x -> {
                    LumberJack roundRobinJack = lumberJacks.get(x % lumberJacks.size());
                    pool.submit(() -> {
                        System.out.println(roundRobinJack + "cuts down tree, " + countDownLatch.getCount() + " left ");
                        roundRobinJack.cut(countDownLatch::countDown);
                    });
                });
        if (!countDownLatch.await(10, TimeUnit.SECONDS)) {
            throw new TimeoutException("Cutting Forest for too long");
        }
        System.out.println("All Trees cut successfully..........");
    }

    private List<LumberJack> generateLumberJacks(int count, Supplier<LumberJack> factory) {
        return IntStream
                .range(0, count)
                .mapToObj(x -> factory.get())
                .collect(Collectors.toList());
    }

    @Override
    public void close() {
        pool.shutdown();
    }
}

/**
 * Lumberjacks are North American workers in the logging industry who perform the initial harvesting
 * and transport of trees for ultimate processing into forest products.
 */
@RequiredArgsConstructor
class LumberJack {

    private final String name;
    private final Lock accessoryOne;
    private final Lock accessoryTwo;

    void cut(Runnable work) {
        try {
            accessoryOne.lock();
            try {
                accessoryTwo.lock();
                work.run();
            } finally {
                accessoryTwo.unlock();
            }
        } finally {
            accessoryOne.unlock();
        }
    }

    public String toString() {
        return "Name : [" + this.name + "]";
    }
}


@RequiredArgsConstructor
class WoodLogging {
    private final RandomStringUtils names;

    private final Lock helmet = new ReentrantLock();
    private final Lock chainsaw = new ReentrantLock();

    // Careful worker first acquires the helmet and then chainsaw before starting the work
    LumberJack careFulWorker() {
        return new LumberJack("Careful_" + names.randomAlphabetic(10), helmet, chainsaw);
    }

    // This is a fundoo guy who just tries to mess up and end up picking chainsaw first and not following the
    // procedure
    LumberJack chillOutWorker() {
        return new LumberJack("Chill-out_" + names.randomAlphabetic(10), chainsaw, helmet);
    }
}
