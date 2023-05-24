package com.cxl.juc.volatile与JMM;

import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 23/5/2023 20:41
 * @ClassReference: com.cxl.juc.volatile与JMM.VolatileSeeDemo
 * @Description:
 * 输出结果：
 * t1     ----come in
 * main	 修改完成 flag:false
 * t1	 ----flag被设置为false
 */
public class VolatileSeeDemo {

//    static boolean flag = true;
    static volatile boolean flag = true;

    public static void main(String[] args) {
        new Thread(() -> {
            System.out.println(Thread.currentThread().getName() + "\t ----come in");

            while (flag) {

            }

            System.out.println(Thread.currentThread().getName() + "\t ----flag被设置为false");

        }, "t1").start();

        try { TimeUnit.SECONDS.sleep(2); }catch (InterruptedException e){ e.printStackTrace(); }

        flag = false;

        System.out.println(Thread.currentThread().getName() + "\t 修改完成 flag:" + flag);
    }
}
