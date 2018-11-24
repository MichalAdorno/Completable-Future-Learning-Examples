package app.example;

import app.data.*;
import app.task.Task;
import app.util.Util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Example2 {

    public static void show() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        //----------------------------------------------------------
        CompletableFuture<A> first = CompletableFuture
                .supplyAsync(() -> Task.doTask(500L, new A("[cf 1]")));

        CompletableFuture<A> second = CompletableFuture
                .supplyAsync(() -> Task.doTask(1000L, new A("[cf 2]")));

        CompletableFuture<String> combined = first
                .thenCombine(
                        second,
                        (A f, A s) -> {
                            Util.printThreadId();
                            return f.val + s.val;
                        }
                );
        //----------------------------------------------------------
        String result = combined.get();
        long endTime = System.currentTimeMillis();
        System.out.printf("Got [%s] in [%d] ms\n", result, endTime - startTime);


    }

}
