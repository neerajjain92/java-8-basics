package com.java8.basics.stream;

import com.java8.basics.UserContextHolder;
import com.java8.basics.UserToBeSharedAcrossService;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author neeraj on 2019-04-02
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class ThreadHolderServiceSharingExample {

    private static ExecutorService threadPool = Executors.newFixedThreadPool(10);

    public static void main(String[] args) {

        for (int i = 0; i < 100; i++) {
            threadPool.submit(() -> {
                new Service1().process();
                System.out.println(Thread.currentThread().getName() + "---- hasName----->" + UserContextHolder.holder.get().getName());
            });
        }
    }
}

class Service1 {
    public void process() {
        UserContextHolder.holder.set(new UserToBeSharedAcrossService());
        new Service2().process();
    }
}


class Service2 {
    public void process() {
        UserToBeSharedAcrossService sharedUser = UserContextHolder.holder.get();
        String renamingTo = "Service2-process()-->" + Thread.currentThread().getName();
        System.out.println(Thread.currentThread().getName() + "---- changing name to ----->" + renamingTo);
        sharedUser.setName(renamingTo);
    }
}
