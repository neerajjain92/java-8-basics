package com.java8.basics;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;
import java.util.concurrent.TimeUnit;

/**
 * @author neeraj on 2019-06-25
 * Copyright (c) 2019, Java-8-Basics.
 * All rights reserved.
 */
public class ForkJoinFramework {

    public static void main(String[] args) {

        // Create the ForkJoinPoo using the default number of processors
        ForkJoinPool forkJoinPool = new ForkJoinPool(Runtime.getRuntime().availableProcessors());

        //Create three FolderProcessor tasks. Initialize each one with a different folder path.
        FolderProcessor desktop = new FolderProcessor("/Users/neeraj/Desktop", "log");
        FolderProcessor downloads = new FolderProcessor("/Users/neeraj/Downloads", "log");
        FolderProcessor github = new FolderProcessor("/Users/neeraj/Projects/office", "log");

        // Execute the 3 tasks in the ForkJoinPool.
        forkJoinPool.execute(desktop);
        forkJoinPool.execute(downloads);
        forkJoinPool.execute(github);

        //Write to the console information about the status of the pool every second
        //until the three tasks have finished their execution.
        do {
            System.out.printf("******************************************\n");
            System.out.printf("Main: Parallelism: %d\n", forkJoinPool.getParallelism());
            System.out.printf("Main: Active Threads: %d\n", forkJoinPool.getActiveThreadCount());
            System.out.printf("Main: Task Count: %d\n", forkJoinPool.getQueuedTaskCount());
            System.out.printf("Main: Steal Count: %d\n", forkJoinPool.getStealCount());
            System.out.printf("******************************************\n");
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } while ((!desktop.isDone()) || (!downloads.isDone()) || (!github.isDone()));

        //Shut down ForkJoinPool using the shutdown() method.
        forkJoinPool.shutdown();

        //Write the number of results generated by each task to the console.
        List<String> results;
        results = desktop.join();
        System.out.printf("Desktop: %d files found.\n", results.size());
        results = downloads.join();
        System.out.printf("Downloads: %d files found.\n", results.size());
        results = github.join();
        System.out.printf("Github: %d files found.\n", results.size());

    }
}

/**
 * * Example Implementations of Fork/Join Pool Framework
 * * <p>
 * * In this example, you will learn how to use the asynchronous methods provided by the ForkJoinPool and ForkJoinTask classes
 * * for the management of tasks. You are going to implement a program that will search for files with a determined extension
 * * inside a folder and its sub-folders.
 * * <p>
 * * The ForkJoinTask class you’re going to implement will process the content of a folder. For each subfolder inside that folder,
 * * it will send a new task to the ForkJoinPool class in an asynchronous way.
 * * For each file inside that folder, the task will check the extension of the file and add it to the result list if it proceeds.
 */
class FolderProcessor extends RecursiveTask<List<String>> {

    //This attribute will store the full path of the folder this task is going to process.
    private final String path;
    //This attribute will store the name of the extension of the files this task is going to look for.
    private final String extension;

    //Implement the constructor of the class to initialize its attributes
    public FolderProcessor(String path, String extension) {
        this.path = path;
        this.extension = extension;
    }

    @Override
    protected List<String> compute() {
        // List to store the names of the files stored in that folder
        List<String> fileNames = new ArrayList<>();

        // FolderProcessor tasks to store the sub-tasks that are going to process the sub-folders stored inside folder.
        List<FolderProcessor> subTasks = new ArrayList<>();

        // Get the content of the folder.
        File file = new File(path);
        File content[] = file.listFiles();

        //For each element in the folder, if there is a subfolder, create a new FolderProcessor object
        //and execute it asynchronously using the fork() method.
        for (int i = 0; i < content.length; i++) {
            if (content[i].isDirectory()) {
                FolderProcessor task = new FolderProcessor(content[i].getAbsolutePath(), extension);
                task.fork();
                subTasks.add(task);
            } else {
                //Otherwise, compare the extension of the file with the extension you are looking for using the checkFile() method
                //and, if they are equal, store the full path of the file in the list of strings declared earlier.

                if (checkFile(content[i].getName())) {
                    fileNames.add(content[i].getAbsolutePath());
                }
            }
        }

        //If the list of the FolderProcessor sub-tasks has more than 50 elements,
        //write a message to the console to indicate this circumstance.
        if (subTasks.size() > 50) {
            System.out.printf("%s: %d tasks ran.\n", file.getAbsolutePath(), subTasks.size());
        }

        // Add the results from the sub-tasks.
        addResultsFrommSubTasks(fileNames, subTasks);

        return fileNames;
    }

    //For each task stored in the list of tasks, call the join() method that will wait for its finalization
    // and then will return the result of the task.
    private void addResultsFrommSubTasks(List<String> fileNames, List<FolderProcessor> subTasks) {
        for (FolderProcessor item : subTasks) {
            fileNames.addAll(item.join());
        }
    }


    //This method compares if the name of a file passed as a parameter ends with the extension you are looking for.
    private boolean checkFile(String name) {
        return name.endsWith(extension);
    }
}
