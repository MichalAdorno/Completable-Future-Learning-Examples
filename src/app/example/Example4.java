package app.example;

import app.data.A;
import app.task.Task;
import app.util.Util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.function.Supplier;

public class Example4 {

    public static void show() throws ExecutionException, InterruptedException {

        int noTasks = 5;
        ExecutorService executor = Executors.newFixedThreadPool(noTasks);
        //a higher order function
        Function<String, Supplier<A>> functor = val -> () -> Task.doTask(800L, new A(val));

        long startTime = System.currentTimeMillis();

        //----------------------------------------------------------
        //Composition vs Combination - COMPOSITION of CompletableFutures
        /*
        CompletableFuture<String> greeting = CompletableFuture
                .supplyAsync(() -> "Hello")
                .thenCompose(s -> CompletableFuture.supplyAsync(() -> s + "World"));
        */

        /*
        === COMPOSITION ===
        It is useful in chain pattern where you required
        to make lots of different calls to gather data
        but are interested ONLY in the actual result of the LAST call.

         */

        //this code runs sequentially, though sometimes in diferent threads
        CompletableFuture<A> composed = CompletableFuture
                .supplyAsync(functor.apply("[cf 0]"), executor)
                .thenComposeAsync(x -> CompletableFuture.supplyAsync(
                        functor.apply(x.val + "[cf 1]")),
                        executor
                ).thenComposeAsync(x -> CompletableFuture.supplyAsync(
                        functor.apply(x.val + "[cf 2]")),
                        executor
                ).thenComposeAsync(x -> CompletableFuture.supplyAsync(
                        functor.apply(x.val + "[cf 3]")),
                        executor
                );


        //----------------------------------------------------------
        A result = composed.get();
        long endTime = System.currentTimeMillis();

        System.out.printf("[COMPOSITION] Got: %s in time: %d\n", result.val, endTime - startTime);

        //----------------------------------------------------------

        long startTime2 = System.currentTimeMillis();

        //----------------------------------------------------------
        //Composition vs Combination - COMBINATION of CompletableFutures

        //this code runs in parallel
        //the total time is the time of the longest running single task + some overhead
        CompletableFuture<A> composed2 = CompletableFuture
                .supplyAsync(functor.apply("[cf 0]"), executor)
                .thenCombine(
                        CompletableFuture.supplyAsync(functor.apply("[cf 1]")),
                        A::reducer
                ).thenCombine(
                        CompletableFuture.supplyAsync(functor.apply("[cf 2]")),
                        A::reducer
                ).thenCombine(
                        CompletableFuture.supplyAsync(functor.apply("[cf 3]")),
                        A::reducer
                );


        //----------------------------------------------------------
        A result2 = composed2.get();
        long endTime2 = System.currentTimeMillis();

        System.out.printf("[COMBINATION] Got: %s in time: %d\n", result2.val, endTime2 - startTime2);

        //----------------------------------------------------------

        long startTime3 = System.currentTimeMillis();

        //----------------------------------------------------------
        //ALL OF

        //this code runs in parallel
        //the total time is the time of the longest running single task + some overhead

        CompletableFuture<A> task0 = CompletableFuture
                .supplyAsync(functor.apply("[cf 0]"), executor);
        CompletableFuture<A> task1 = CompletableFuture
                .supplyAsync(functor.apply("[cf 1]"), executor);
        CompletableFuture<A> task2 = CompletableFuture
                .supplyAsync(functor.apply("[cf 2]"), executor);
        CompletableFuture<A> task3 = CompletableFuture
                .supplyAsync(functor.apply("[cf 3]"), executor);

        CompletableFuture<?> barrier = CompletableFuture.allOf(task0,task1,task2,task3);

        //----------------------------------------------------------
        barrier.join();
        A result3 = composed2.get();
        long endTime3 = System.currentTimeMillis();

        System.out.printf("[allOf] Got: %s in time: %d\n", result3.val, endTime3 - startTime3);


    }

}
