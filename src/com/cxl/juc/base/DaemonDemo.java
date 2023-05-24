package com.cxl.juc.base;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 21/5/2023 09:30
 * @ClassReference: com.cxl.juc.base.DaemonDemo
 * @Description:
 */
public class DaemonDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t 开始运行，" +
                    (Thread.currentThread().isDaemon() ? "守护线程" : "用户线程"));
            while (true) {

            }
        }, "t1");

        // 该方法必须设置在start()方法之前，不然会包IllegalThreadStateException
        t1.setDaemon(true);
        t1.start();

        // 暂停3秒钟线程
        try { TimeUnit.SECONDS.sleep(3); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + "\t ----end 主线程");
    }
}
