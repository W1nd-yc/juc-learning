package com.cxl.juc.objecthead;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.vm.VM;

/**
 * @Author cxl
 * @Date 29/5/2023 21:14
 * @ClassReference: com.cxl.juc.objecthead.JOLDemo
 * @Description:
 *

 */
public class JOLDemo {

    public static void main(String[] args) {

        Object o = new Object(); // 16 bytes

//        System.out.println(ClassLayout.parseInstance(o).toPrintable());

        Customer c1 = new Customer();
        System.out.println(ClassLayout.parseInstance(c1).toPrintable());
    }
}

/**
 * 1.默认配置，启动了压缩指针，-XX:+UseCompressedClassPointers
 * 12+4(对齐填充) = 一个对象16个字节
 *
 * 2.手动配置，关闭了压缩指针 -XX:-UseCompressedClassPointers
 * 8+8=16个字节
 */
class Customer {

    // 第一种情况，只有对象头，没有任何其它实例数据

    // 第二种情况 int + boolean，默认满足对齐填充，24 bytes
    int id;
    boolean flag = false;
}