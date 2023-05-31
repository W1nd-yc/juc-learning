package com.cxl.juc.objecthead;

/**
 * @Author cxl
 * @Date 29/5/2023 20:27
 * @ClassReference: com.cxl.juc.objecthead.ObjectHeadDemo
 * @Description:
 */
public class ObjectHeadDemo {

    public static void main(String[] args) {
        Object o = new Object(); // new一个对象，占内存多少
        System.out.println(o.hashCode()); // 这个hashcode记录在对象的什么地方

        synchronized (o) {

        }

        System.gc(); // 手动收集垃圾。。。超过15次可以从新生代---养老区

        Custom c1 = new Custom();
    }
}

// 只有一个对象的实例对象，16字节（忽略压缩指针的影响）+4字节（int）+1字节（boolean）=21字节  ---》 对齐填充，24字节
class Custom {

    int id;
    boolean flag = false;
}
