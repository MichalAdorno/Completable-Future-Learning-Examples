package app.util;

import java.util.concurrent.CompletableFuture;

public class Util {

    public static void printThreadId(){
        System.out.printf("[Thread-%s]\n", Thread.currentThread().getId());
    }

    public static void assertAndPrintIsDone(CompletableFuture<?> cf, String name){
        assert(cf.isDone());
        System.out.printf("[CF %s isDone?-%b]\n", name, cf.isDone());
    }

}
