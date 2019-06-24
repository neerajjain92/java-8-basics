package com.java8.basics;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * https://www.youtube.com/watch?v=WN9kgdSVhDo
 * Fluent Interfaces API....
 * <p>
 * Using ==> Consumer
 *
 * @author neeraj on 2019-06-23
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class FluentInterfacesUsingLambdaExpressions {

    public static void main(String[] args) {
        // Traditional Way
        System.out.println("=========Traditional Way without Builder Pattern============");

        Mailer mailer = new Mailer();

        mailer.from("builder@world.com");
        mailer.to("neerajjain1392@gmail.com");
        mailer.subject("Your Code sucks");
        mailer.body("...details...");
        mailer.send();

        // Reducing the Noise by making it using builder pattern
        // Every method returns the object itself
        // and use the Builder pattern
        // Let's do it in ModifiedMailer Class

        System.out.println("============Fluent Interfaces Leveraging Consumer =========");

        ModifiedMailer.send(modifiedMailer ->
                modifiedMailer.from("builder@world.com")
                        .to("neerajjain1392@gmail.com")
                        .subject("Your Code sucks")
                        .body("...Details..."));

        Map<String, Integer> nameMap = new HashMap<>();
        nameMap.computeIfAbsent("John", s -> s.length());
    }
}

class ModifiedMailer {
    public ModifiedMailer from(String addr) {
        System.out.println("From : " + addr);
        return this;
    }

    public ModifiedMailer to(String addr) {
        System.out.println("to : " + addr);
        return this;
    }

    public ModifiedMailer subject(String line) {
        System.out.println("subject : " + line);
        return this;
    }

    public ModifiedMailer body(String text) {
        System.out.println("body : " + text);
        return this;
    }

    public static void send(Consumer<ModifiedMailer> consumer) {
        ModifiedMailer modifiedMailer = new ModifiedMailer();
        consumer.accept(modifiedMailer);
        System.out.println(".....Sending....");
    }
}

class Mailer {
    public void from(String addr) {
        System.out.println("From : " + addr);
    }

    public void to(String addr) {
        System.out.println("to : " + addr);
    }

    public void subject(String line) {
        System.out.println("subject : " + line);
    }

    public void body(String text) {
        System.out.println("body : " + text);
    }

    public void send() {
        System.out.println(".....Sending....");
    }
}
