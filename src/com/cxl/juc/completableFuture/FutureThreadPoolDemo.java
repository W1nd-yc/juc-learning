package com.cxl.juc.completableFuture;

import java.util.concurrent.*;

/**
 * @Author cxl
 * @Date 21/5/2023 09:56
 * @ClassReference: com.cxl.juc.completableFuture.FutureThreadPoolDemo
 * @Description:
 */
public class FutureThreadPoolDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        // 3个任务，目前开启多个异步任务线程来处理，请问耗时多少（未使用get()方法357毫秒，使用get()方法894毫秒）
        ExecutorService theadPool = Executors.newFixedThreadPool(3);

        long startTime = System.currentTimeMillis();

        FutureTask<String> futureTask1 = new FutureTask<>(() -> {
            try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }
            return "task1 over";
        });
        theadPool.submit(futureTask1);

        FutureTask<String> futureTask2 = new FutureTask<>(() -> {
            try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }
            return "task1 over";
        });
        theadPool.submit(futureTask2);

        System.out.println(futureTask1.get());
        System.out.println(futureTask2.get());

        try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }

        long endTime = System.currentTimeMillis();
        System.out.println("---constTime:" + (endTime - startTime) + "毫秒");
        theadPool.shutdown();
    }

    private static void m1() {
        // 3个任务，目前只有一个线程main来处理，请问耗时多少（1125毫秒）
        long startTime = System.currentTimeMillis();

        try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }
        try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }
        try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }

        long endTime = System.currentTimeMillis();
        System.out.println("---constTime:" + (endTime - startTime) + "毫秒");

        System.out.println(Thread.currentThread().getName() + "\t -----end");
    }
}
