package com.java8.basics;

/**
 * @author neeraj on 2019-04-02
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class UserContextHolder {

    public static ThreadLocal<UserToBeSharedAcrossService> holder = new ThreadLocal<>();
}
