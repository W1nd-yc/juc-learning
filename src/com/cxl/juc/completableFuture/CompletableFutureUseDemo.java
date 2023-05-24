package com.cxl.juc.completableFuture;

import java.util.concurrent.*;

/**
 * @Author cxl
 * @Date 21/5/2023 12:08
 * @ClassReference: com.cxl.juc.completableFuture.CompletableFutureIseDemo
 * @Description:
 */
public class CompletableFutureUseDemo {

    /**
     * 出现异常时输出结果：
     * pool-1-thread-1----come in
     * main线程先去忙其他任务
     * -----1秒钟后出结果：1
     * 异常情况：java.lang.ArithmeticException: / by zero	java.lang.ArithmeticException: / by zero
     * java.util.concurrent.CompletionException: java.lang.ArithmeticException: / by zero
     *
     * 未出现异常时的结果：
     * pool-1-thread-1----come in
     * main线程先去忙其他任务
     * -----1秒钟后出结果：3
     * -----计算完成，更新系统UpdateValue：3
     */
    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ExecutorService threadPool = Executors.newFixedThreadPool(3);

        try {
            CompletableFuture.supplyAsync(() -> {
                System.out.println(Thread.currentThread().getName() + "----come in");
                int result = ThreadLocalRandom.current().nextInt(10);
                try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println("-----1秒钟后出结果：" + result);
                if (result > 5) {
                    int i = 10/0;
                }
                return result;
            }, threadPool).whenComplete((v, e) -> {
                if (e == null) {
                    System.out.println("-----计算完成，更新系统UpdateValue：" + v);
                }
            }).exceptionally(e -> {
                e.printStackTrace();
                System.out.println("异常情况：" + e.getCause() + "\t" + e.getMessage());
                return null;
            });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            threadPool.shutdown();
        }

        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        // 主线程不要立刻结束，否则CompletableFuture默认使用的线程池会立刻关闭
        // try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }
    }

    /**
     * 返回结果
     * main线程先去忙其他任务
     * ForkJoinPool.commonPool-worker-9----come in
     * -----3秒钟后出结果：2
     * 2
     */
    private static void future1() throws InterruptedException, ExecutionException {
        CompletableFuture<Integer> completableFuture = CompletableFuture.supplyAsync(() -> {
            System.out.println(Thread.currentThread().getName() + "----come in");
            int result = ThreadLocalRandom.current().nextInt(10);
            // 暂停3秒钟线程
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("-----3秒钟后出结果：" + result);
            return result;
        });

        System.out.println(Thread.currentThread().getName() + "线程先去忙其他任务");

        System.out.println(completableFuture.get());
    }
}
