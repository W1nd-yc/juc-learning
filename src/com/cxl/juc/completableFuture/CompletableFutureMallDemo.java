package com.cxl.juc.completableFuture;
import javax.swing.plaf.basic.BasicSplitPaneUI;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * @Author cxl
 * @Date 21/5/2023 14:03
 * @ClassReference: com.cxl.juc.completableFuture.CompletableFutureMallDemo
 * @Description:
 */
public class CompletableFutureMallDemo {

    static List<NetMall> list = Arrays.asList(
            new NetMall("jd"),
            new NetMall("dangdang"),
            new NetMall("taobao")
    );

    /**
     * step by step
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPrice(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> String.format(productName + "in %s price is %.2f",
                netMall.getNetMallName(),
                netMall.calcPrice(productName))).collect(Collectors.toList());
    }

    /**
     * all in
     * @param list
     * @param productName
     * @return
     */
    public static List<String> getPriceByCompletableFuture(List<NetMall> list, String productName) {
        return list.stream().map(netMall -> CompletableFuture.supplyAsync(() -> String.format(productName + "in %s price is %.2f",
                netMall.getNetMallName(),
                netMall.calcPrice(productName))))
                .collect(Collectors.toList())
                .stream()
                .map(s -> s.join())
                .collect(Collectors.toList());
    }

    /**
     * 输出结果：
     * mysqlin jd price is 109.02
     * mysqlin dangdang price is 109.92
     * mysqlin taobao price is 110.56
     * ----constTime:3117毫秒
     * --------------------------
     * mysqlin jd price is 109.50
     * mysqlin dangdang price is 109.85
     * mysqlin taobao price is 109.20
     * ----constTime:1013毫秒
     */
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        List<String> list1 = getPrice(list, "mysql");
        for (String element : list1) {
            System.out.println(element);
        }
        long endTime = System.currentTimeMillis();
        System.out.println("----constTime:" + (endTime - startTime) + "毫秒");

        System.out.println("--------------------------");

        long startTime2 = System.currentTimeMillis();
        List<String> list2 = getPriceByCompletableFuture(list, "mysql");
        for (String element : list2) {
            System.out.println(element);
        }
        long endTime2 = System.currentTimeMillis();
        System.out.println("----constTime:" + (endTime2 - startTime2) + "毫秒");
    }
}

class NetMall {

    private String netMallName;

    public String getNetMallName() {
        return netMallName;
    }

    public NetMall(String netMallName) {
        this.netMallName = netMallName;
    }

    public double calcPrice(String productName) {
        // 暂停1秒钟线程
        try { TimeUnit.SECONDS.sleep(1); }catch (InterruptedException e){ e.printStackTrace(); }
        return ThreadLocalRandom.current().nextDouble() * 2 + productName.charAt(0);
    }
}

