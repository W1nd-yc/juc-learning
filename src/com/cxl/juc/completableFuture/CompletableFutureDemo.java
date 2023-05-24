package com.cxl.juc.completableFuture;

import java.util.concurrent.*;

/**
 * @Author cxl
 * @Date 21/5/2023 11:14
 * @ClassReference: com.cxl.juc.completableFuture.CompletableFutureDemo
 * @Description:
 */
public class CompletableFutureDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        // 输出结果：
        // pool-1-thread-1
        // null
        /*CompletableFuture<Void> completableFuture = CompletableFuture.runAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            // 暂停3秒钟线程
            try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
        }, threadPool);

        System.out.println(completableFuture.get());*/

        // 输出结果：
        // pool-1-thread-1
        // hello supplyAsync
        CompletableFuture<String> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName());
            // 暂停3秒钟线程
            try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
            return "hello supplyAsync";
        }, threadPool);

        System.out.println(completableFuture.get());

        threadPool.shutdown();
    }
}
