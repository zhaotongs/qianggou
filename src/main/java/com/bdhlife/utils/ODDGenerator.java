package com.bdhlife.utils;
import java.time.Instant;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.FastDateFormat;

/**
 * 订单编号策略
 *
 * @project common-utils
 * @fileName ODDGenerator.java
 * @Description
 * @author light-zhang
 * @date 2018年5月11日
 * @version 1.0.0
 */
public class ODDGenerator {
    private static final FastDateFormat pattern = FastDateFormat.getInstance("yyyyMMddHHmmss");
    private static final AtomicInteger atomicInteger = new AtomicInteger(1);
    private static ThreadLocal<StringBuilder> threadLocal = new ThreadLocal<StringBuilder>();

    /**
     * 【长码生成策略】
     *
     * @时间20180511231532
     * @二位随机数
     * @lock的hash-code编码
     * @param lock 生成的UUID32位参数
     * @return 长码机制
     */
    public static String getNumber() {
        StringBuilder builder = new StringBuilder(pattern.format(Instant.now().toEpochMilli()));// 取系统当前时间作为订单号前半部分
        builder.append(atomicInteger.getAndIncrement());// 自增顺序
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }

    /**
     * 【短码生成策略】
     *
     * @param lock
     * @return
     */
    /*public static String getD() {
        StringBuilder builder = new StringBuilder(ThreadLocalRandom.current().nextInt(0,999));// 随机数
        builder.append(atomicInteger.getAndIncrement());// 自增顺序
        threadLocal.set(builder);
        return threadLocal.get().toString();
    }*/

    /**
     * 测试
     *
     * @param args
     * @throws InterruptedException
     * @throws ExecutionException
     */
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        for (int i = 0; i < 20; i++) {
            System.out.println(getNumber());
        }
    }
}
