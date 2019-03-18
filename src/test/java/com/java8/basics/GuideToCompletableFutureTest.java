package com.java8.basics;

import com.google.common.base.Stopwatch;
import com.java8.basics.stream.GuideToCompletableFuture;
import org.junit.Before;
import org.junit.Test;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

/**
 * @author neeraj on 2019-03-18
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class GuideToCompletableFutureTest {

    GuideToCompletableFuture guideToCompletableFuture;

    @Before
    public void init() {
        guideToCompletableFuture = new GuideToCompletableFuture();
    }

    @Test
    public void testGetCompletableFuture() throws ExecutionException, InterruptedException {
        Stopwatch stopwatch = Stopwatch.createStarted();
        Future<String> completableFuture = guideToCompletableFuture.getCompletableFuture();
        assertNotNull("Completable Future is not null", completableFuture);
        String value = completableFuture.get();
        assertEquals("future", value);
        assertTrue("CompletableFuture completed after 5 seconds", stopwatch.elapsed(TimeUnit.SECONDS) > 4L);
    }
}
