package com.cxl.juc.base;

/**
 * @Author cxl
 * @Date 17/5/2023 17:14
 * @ClassReference: com.cxl.juc.base.ThreadBaseDemo
 * @Description:
 */
public class ThreadBaseDemo {

    public static void main(String[] args) {
        Thread t1 = new Thread(() -> {

        }, "t1");
        t1.start();

    }
}
