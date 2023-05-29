package com.cxl.juc.原子操作类;

import java.util.concurrent.atomic.LongAccumulator;
import java.util.concurrent.atomic.LongAdder;
import java.util.function.LongBinaryOperator;

/**
 * @Author cxl
 * @Date 25/5/2023 15:01
 * @ClassReference: com.cxl.juc.原子操作类.LongAddrAPIDemo
 * @Description:
 */
public class LongAdderAPIDemo {

    public static void main(String[] args) {
        LongAdder longAdder = new LongAdder();

        longAdder.increment();
        longAdder.increment();
        longAdder.increment();

        System.out.println(longAdder.sum());

//        LongAccumulator longAccumulator = new LongAccumulator((x, y) -> x+y, 0);
        LongAccumulator longAccumulator = new LongAccumulator(new LongBinaryOperator() {
            @Override
            public long applyAsLong(long left, long right) {
                return left + right;
            }
        }, 0);

        longAccumulator.accumulate(1);// 1
        longAccumulator.accumulate(3);// 4

        System.out.println(longAccumulator.get());
    }
}
