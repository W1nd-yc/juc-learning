package com.cxl.juc.completableFuture;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

/**
 * @Author cxl
 * @Date 21/5/2023 09:44
 * @ClassReference: com.cxl.juc.completableFuture.CompletableFutureDemo
 * @Description:
 */
public class FutureTaskDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        java.util.concurrent.FutureTask<String> futureTask = new java.util.concurrent.FutureTask<>(new MyThread());

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        System.out.println(futureTask.get());
    }
}

class MyThread implements Callable<String> {

    @Override
    public String call() throws Exception {
        System.out.println("----come in call()");
        return "hello";
    }
}
