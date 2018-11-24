package app.example;

import app.util.Util;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Example3 {

    public static void show() throws ExecutionException, InterruptedException {

        ExecutorService executor = Executors.newFixedThreadPool(5);

        //----------------------------------------------------------

        CompletableFuture<?> task = CompletableFuture.supplyAsync(
                () -> {
                    Util.printThreadDiagnostics("supplyAsync");
                    return 5;
                },
                executor
        ).thenApply(
                i -> {      //Stream.map
                    Util.printThreadDiagnostics("thenApply");
                    return i * 3;
                }
        ).thenAccept(       //Stream.forEach
                i -> {
                    Util.printThreadDiagnostics("thenAccept");
                    System.out.println("The result is " + i);
                }
        ).thenRunAsync(
                () -> {
                    Util.printThreadDiagnostics("thenRunAsync");
                    System.out.println("Finished Async.");
                },
                executor
        );
        //in a new thread when with the "*Async"-suffix,
        //in the same without the "*Async"-suffix
        //----------------------------------------------------------
        task.join();

    }

}
