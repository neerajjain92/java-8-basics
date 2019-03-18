package com.java8.basics.stream;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author neeraj on 2019-03-18
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class GuideToCompletableFuture {

    public Future<String> getCompletableFuture() {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();

        Executors.newCachedThreadPool().submit(() -> {
            Thread.sleep(5000);
            completableFuture.complete("future");
            return null;
        });
        return completableFuture;
    }
}
