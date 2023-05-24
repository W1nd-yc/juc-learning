package com.cxl.juc.锁;

import java.util.Date;

/**
 * @Author cxl
 * @Date 21/5/2023 16:59
 * @ClassReference: com.cxl.juc.锁.LockSyncDemo
 * @Description:
 */

class Book extends Object{

}

public class LockSyncDemo {

    /*Object object = new Object();

    public void m1() {
        synchronized (object) {
            System.out.println("----hello synchronized code block");
        }
    }*/

    public static synchronized void m3() {
        System.out.println("----hello synchronized m3");
    }

    public static void main(String[] args) {

    }
}
