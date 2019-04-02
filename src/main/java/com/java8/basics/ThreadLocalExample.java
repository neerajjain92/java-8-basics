package com.java8.basics;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author neeraj on 2019-04-01
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class ThreadLocalExample {

    private static ThreadLocal<SimpleDateFormat> dateFormatter = ThreadLocal.withInitial(() -> new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS"));
    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");

    public static void main(String[] args) {
        // 1000 tasks
        for (int i = 0; i < 200; i++) {
            threadPool.submit(() -> {
                Date date = new Date();
                ThreadLocalExample threadLocalExample = new ThreadLocalExample();
                String formattedDate = threadLocalExample.birthDateWithThreadLocal(date);
                System.out.println(Thread.currentThread().getName() + "::::::" + threadLocalExample.simpleDateFormat.format(date));
                System.out.println(Thread.currentThread().getName() + " ===> Formatted Date :: " + formattedDate);
            });
        }
    }

    private String birthDate(Date dateToFormat) {
        return simpleDateFormat.format(dateToFormat);
    }

    private String birthDateWithThreadLocal(Date dateToFormat) {
        return ThreadLocalExample.dateFormatter.get().format(dateToFormat);
    }
}
