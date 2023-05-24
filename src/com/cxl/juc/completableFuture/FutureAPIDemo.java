package com.cxl.juc.completableFuture;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

/**
 * @Author cxl
 * @Date 21/5/2023 10:07
 * @ClassReference: com.cxl.juc.completableFuture.FutureAPIDemo
 * @Description:
 * 1.get容易导致阻塞，一般建议放在程序后后面，一旦调用不见不散，非要等到结果才会离开，不管你是否计算完成，容器程序堵塞
 * 2.假如我不愿意等待很长时间，我希望过时不候，可以自动离开
 */
public class FutureAPIDemo {

    public static void main(String[] args) throws ExecutionException, InterruptedException, TimeoutException {
        FutureTask<String> futureTask = new FutureTask<>(() -> {
            System.out.println(Thread.currentThread().getName() + "\t -----come in");
            // 暂停3秒钟线程
            try { TimeUnit.SECONDS.sleep(5); }catch (InterruptedException e){ e.printStackTrace(); }
            return "task over";
        });

        Thread t1 = new Thread(futureTask, "t1");
        t1.start();

        System.out.println(Thread.currentThread().getName() + "\t ---忙其他任务了");

//        System.out.println(futureTask.get());
//        System.out.println(futureTask.get(3, TimeUnit.SECONDS));

        while(true) {
            if (futureTask.isDone()) {
                System.out.println(futureTask.get());
                break;
            } else {
                try { TimeUnit.MILLISECONDS.sleep(500); }catch (InterruptedException e){ e.printStackTrace(); }
                System.out.println("正在处理中，不要再催了，越催越慢，再催熄火");
            }
        }
    }
}
