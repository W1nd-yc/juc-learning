package com.cxl.juc.completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 21/5/2023 14:51
 * @ClassReference: com.cxl.juc.completableFuture.CompletableFutureAPI2Demo
 * @Description:
 */
public class CompletableFutureAPI2Demo {

    /**
     * 输出结果：
     * main----主线程先去忙其他任务
     * 111
     * 222
     * 333
     * ---计算结果：6
     *
     * thenApply发生异常时，会叫停，不会继续往下走
     */
    public static void main(String[] args) {
        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        CompletableFuture.supplyAsync(() -> {
            // 暂停1秒钟线程
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("111");
            return 1;
        }, threadPool).handle((f, e) -> {
            int i = 10/0;
            System.out.println("222");
            return f+2;
        }).handle((f, e) -> {
            System.out.println("333");
            return f+3;
        }).whenComplete((v, e) -> {
            if (e == null) {
                System.out.println("---计算结果：" + v);
            }
        }).exceptionally(e -> {
            e.printStackTrace();
            System.out.println(e.getMessage());
            return null;
        });

        System.out.println(Thread.currentThread().getName() + "----主线程先去忙其他任务");

        threadPool.shutdown();
    }
}
