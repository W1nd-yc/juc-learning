package com.cxl.juc.cas;

import com.sun.corba.se.impl.oa.poa.AOMEntry;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Author cxl
 * @Date 24/5/2023 14:52
 * @ClassReference: com.cxl.juc.cas.CASDemo
 * @Description:
 */
public class CASDemo {

    public static void main(String[] args) {
        AtomicInteger atomicInteger = new AtomicInteger(5);

        System.out.println(atomicInteger.compareAndSet(5, 2022) + "\t" + atomicInteger.get());

        atomicInteger.getAndIncrement();
    }
}
