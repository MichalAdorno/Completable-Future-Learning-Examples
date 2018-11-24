package app.util;

import java.util.Random;
import java.util.concurrent.CompletableFuture;

public class Util {

    public static void printThreadDiagnostics(long timeout, String info) {
        System.out.printf(
                "[Thread-%s][%d]%s\n",
                Thread.currentThread().getId(),
                timeout,
                info
        );
    }

    public static void printThreadId() {
        System.out.printf("[Thread-%s]\n", Thread.currentThread().getId());
    }

    public static void assertAndPrintIsDone(CompletableFuture<?> cf, String name) {
        assert (cf.isDone());
        System.out.printf("[CF %s isDone?-%b]\n", name, cf.isDone());
    }

    public static long random(){
        float generatedFloat = new Random().nextFloat() * 3000;
        return (long) generatedFloat;
    }

}
