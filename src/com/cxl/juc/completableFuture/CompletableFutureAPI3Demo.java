package com.cxl.juc.completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * @Author cxl
 * @Date 21/5/2023 15:00
 * @ClassReference: com.cxl.juc.completableFuture.CompletableFutureAPI3Demo
 * @Description:
 */
public class CompletableFutureAPI3Demo {

    public static void main(String[] args) {
        /*CompletableFuture.supplyAsync(() -> {
            return 1;
        }).thenApply(f -> {
            return f+2;
        }).thenApply(f -> {
            return f+3;
        }).thenAccept(System.out::println);*/

        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenRun(() -> {}).join());
        System.out.println();
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenAccept(r -> System.out.println(r)).join());
        System.out.println();
        System.out.println(CompletableFuture.supplyAsync(() -> "resultA").thenApply(r -> r + "resultB").join());
    }
}
