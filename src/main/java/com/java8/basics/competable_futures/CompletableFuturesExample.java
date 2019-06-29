package com.java8.basics.competable_futures;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.function.Consumer;
import java.util.function.Function;

import static com.java8.basics.LogUtil.logIt;
import static com.java8.basics.parallel_and_asynchronous_programming.StreamsExample.printIt;
import static java.lang.Thread.sleep;

/**
 * Completable Futures in Java are similar to promises in JavaScript.
 * <p>
 * // Famous or popular Functional interfaces
 * //   >>>NAME<<<<        >>>Method<<<<                >>>>Users<<<<
 * // Supplier Supplier<T>  T get()               ===> factories uses Supplier
 * // Predicate<T>         boolean test(T)        ===> filter uses Predicate
 * // Function<T, R>       R apply(T)             ===> map uses Function
 * // Consumer<T>          void accept(T)         ====> forEach uses Consumer
 *
 * @author neeraj on 2019-06-29
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class CompletableFuturesExample {

    /**
     * Simple example of CompletableFuture which supplies value Asynchronously.
     */
    public static CompletableFuture<Integer> create(boolean shouldSleep) {
        // Supplying the value Asynchronously, hence you see compute method runs in Thread[ForkJoinPool.commonPool-worker
        return CompletableFuture.supplyAsync(() -> compute(shouldSleep));
    }

    public static CompletableFuture<Integer> createCompletableFutureWithItsOwnPool(boolean shouldSleep) {
        ForkJoinPool forkJoinPool = new ForkJoinPool(10);
        return CompletableFuture.supplyAsync(() -> compute(shouldSleep), forkJoinPool);
    }

    public static int compute(boolean shouldSleep) {
        System.out.println("compute -- " + Thread.currentThread());
        if (shouldSleep) {
            try {
                sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return 2;
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        logIt("Completable Future in Java is Promises in JavaScript", true);
        logIt("Stages -- \n 1) Resolved \n 2) Rejected \n 3) Pending \n", true);

        logIt("Creating a completable Future", true);
        CompletableFuture<Integer> future = create(false);

        sleep(100);

        // "then" prefix comes from JavaScript, where promises are "thenable"
        future.thenAccept(data -> System.out.println("Printing Result of Completable Future --> " + data));


        // Completable Future never dies, it keeps on giving new completable future, So in above example
        // if we get the result we will get CompletableFuture<Void>. Why void? because thenAccept(takes a Consumer)
        // And we all know consumer just accepts a value it never returns it.
        logIt("Completable Future never dies and keep on retuning new completable future endlessly", true);
        CompletableFuture<Void> future2 = future.thenAccept(data -> System.out.println("Printing Result of Completable Future  and it returns CompletableFuture of type <void> --> " + data));


        // So to show that completable future never dies, let me show you that
        // but before that Do you know which Interface is there in java which takes nothing
        // and returns nothing.....yeah you guessed it right, that's Runnable for us.

        logIt("Completable Futures never dies", true);

        create(false)
                .thenAccept(data -> System.out.println(data))
                .thenRun(() -> System.out.println("This never dies"))
                .thenRun(() -> System.out.println("Really,This never dies"))
                .thenRun(() -> System.out.println("Really, really This never dies... There is no end to it"))
                .thenRun(() -> System.out.println("So I am out of here"));


        logIt("Let's look into get() and getNow() methods", true);
        // Completable Future.get() method is a blocking call, and it's the bad Idea to use it.
        // So always use thenAccept to be asynchronous.
        System.out.println(create(false).get()); // Well this is a Blocking call and you will see the print statement after this line will print afterwards
        System.out.println("Statement will be executed after completableFuture#get() blocking call");

        // getNow() this is impatient non-blocking call, it just asks for the result
        // if the data is available it will give you the data else it will give you whatever argument you gave.
        System.out.println("Get Now >>> " + create(false).getNow(0));

        // In case of CompletableFutures if the completableFuture is completed and data is available main thread doesn't switch to another thread instead completes the task itself.
        logIt("Thread of Execution, how execution works ", true);
        logIt("In case of CompletableFutures if the completableFuture is completed and data is available main thread doesn't switch to another thread instead completes the task itself.", true);
        logIt("In Main Thread -- " + Thread.currentThread());

        future = create(true);

        future.thenAccept(data -> printIt(data));

        System.out.println("Hello I am done and i will be printed before completableFuture#thenAccept --" + Thread.currentThread());

        // This sleep is to show that printIt method will eventually run in a separate thread since completableFuture was not ready when main encountered the thenAccept line.
        sleep(1000);

        logIt("Changing the ForkJoinPool in which Completable Future should execute and you will see with this it will not run in the common pool", true);
        future = createCompletableFutureWithItsOwnPool(false);

        future.thenAccept(data -> System.out.println("Printing data after changing the pool :: " + data));


        /**
         *  Methods now we want to talk about is
         * {@link CompletableFuture#thenAccept(Consumer)}
         * {@link CompletableFuture#thenRun(Runnable)}
         */
        logIt("Let's see thenAccept and thenRun in combination", true);
        create(false)
                .thenAccept(CompletableFuturesExample::writeToDatabase)
                .thenRun(() -> System.out.println("Transaction successfully written in database"));

        /**
         * Comparision between Streams and Completable
         *
         *  Stream                         Completable Future
         *  pipeline                        pipeline                     ------> Both have pipeline
         *  lazy                            lazy                         ------>  Both are lazy
         *  Zero one or more data           zero or one data             ------> Stream operates on list of values generally, where as completable futures works as promises and always resolve to one or zero value
         *  only data channel               data channel and error channel -----> error channel for all errors
         *  forEach                         thenAccept              ------> Getting the value
         *  map                             thenApply               ------> Transforming the value from one to another
         */

        /**
         * {@link CompletableFuture#thenApply(Function)}
         */
        logIt("Let's talk about thenApply()", true);
        create(false)
                .thenApply(data -> data * 10)
                .thenAccept(data -> writeToDatabase(data))
                .thenRun(() -> System.out.println("Transaction successfully written in database after performing transformation"));
    }

    private static void writeToDatabase(Integer data) {
        try {
            logIt("Writing transaction data " + data + " to database and it will take around 2 Seconds.....");
            sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
