package app;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;

public class Task {

    public static <T extends Base> T doTask(Long timeout, T t){
        try{
            Thread.sleep(timeout);
            printThreadId();
            return t;
        } catch(InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    public static void printThreadId(){
        System.out.printf("[Thread-%s]\n", Thread.currentThread().getId());
    }

    public static void assertAndPrintIsDone(CompletableFuture<?> cf, String name){
        assert(cf.isDone());
        System.out.printf("[CF %s isDone?-%b]\n", name, cf.isDone());
    }
}
