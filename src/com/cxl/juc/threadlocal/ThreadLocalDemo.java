package com.cxl.juc.threadlocal;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @Author cxl
 * @Date 29/5/2023 16:28
 * @ClassReference: com.cxl.juc.threadlocal.ThreadLocalDemo
 * @Description: 需求1： 5个销售卖房子，集团高层只关心销售总量的准确统计数
 */
class House {

    int saleCount = 0;

    public synchronized void saleHouse() {
        ++saleCount;
    }

    /*ThreadLocal<Integer> saleVolume = new ThreadLocal<Integer>() {

        @Override
        protected Integer initialValue() {
            return super.initialValue();
        }
    };*/

    ThreadLocal<Integer> saleVolume = ThreadLocal.withInitial(() -> 0);

    public void saleVolumeByThreadLocal() {
        saleVolume.set(1 + saleVolume.get());
    }
}

public class ThreadLocalDemo {

    public static void main(String[] args) {
        House house = new House();

        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
                int size = new Random().nextInt(5)+1;
//                System.out.println(size);

                try {
                    for (int j = 0; j < size; j++) {
                        house.saleHouse();
                        house.saleVolumeByThreadLocal();
                    }
                    System.out.println(Thread.currentThread().getName() + "\t 号销售卖出：" + house.saleVolume.get());
                } finally {
                    house.saleVolume.remove();
                }
            }, String.valueOf(i)).start();
        }

        try { TimeUnit.MILLISECONDS.sleep(300); }catch (InterruptedException e){ e.printStackTrace(); }

        System.out.println(Thread.currentThread().getName() + "\t 共计卖出多少套：" + house.saleCount);
    }
}
