package app;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class Example1 {
    public static void show() throws ExecutionException, InterruptedException {
        long startTime = System.currentTimeMillis();
        //----------------------------------------------------------

        CompletableFuture<A> aCF = CompletableFuture
                .supplyAsync(() -> Task.doTask(1000L, new A("AAA")));
        CompletableFuture<B> bCF = CompletableFuture
                .supplyAsync(() -> Task.doTask(500L, new B("BBB")));
        CompletableFuture<C> cCF = CompletableFuture
                .supplyAsync(() -> Task.doTask(500L, new C("CCC")));

        CompletableFuture<Void> barrier = CompletableFuture
                .allOf(aCF, bCF, cCF);

        Task.printThreadId();
        //----------------------------------------------------------

        barrier.join();
        long endTime1 = System.currentTimeMillis();
        Task.assertAndPrintIsDone(aCF, "aCF");
        Task.assertAndPrintIsDone(bCF, "bCF");
        Task.assertAndPrintIsDone(cCF, "cCF");
        //----------------------------------------------------------

        A aResult = aCF.get();
        B bResult = bCF.get();
        C cResult = cCF.get();

        long endTime2 = System.currentTimeMillis();
        //----------------------------------------------------------

        System.out.printf("[end1 - start] %d\n", endTime1 - startTime);
        System.out.printf("[end2 - start] %d\n", endTime2 - startTime);

        System.out.printf("[A-result] %s\n", aResult);
        System.out.printf("[B-result] %s\n", bResult);
        System.out.printf("[C-result] %s\n", cResult);
        Task.printThreadId();
        //----------------------------------------------------------

    }
}
