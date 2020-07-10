package com.cloudbest.order.utils;

import cn.hutool.core.date.DateUtil;
import io.netty.util.HashedWheelTimer;
import io.netty.util.Timeout;
import io.netty.util.TimerTask;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class TimeTaskUtil {
    public static void main(String[] args) {
//        System.out.println("11111"+LocalDateTime.now());
//        NettyTask();
//        System.out.println("222222");
        test2();
    }

    public static void test2(){
        LocalDateTime overTime = LocalDateTime.now().plusMinutes(-1);
        Date date = Date.from(overTime.toInstant(ZoneOffset.of("+8")));

        long l = DateUtil.betweenMs(com.cloudbest.common.util.DateUtil.getCurrDate(), date);
        System.out.println(l);
    }
    /**
     * 基于 Netty 的延迟任务
     */
    public static void NettyTask() {
        // 创建延迟任务实例
        HashedWheelTimer timer = new HashedWheelTimer(10, // 时间间隔
                TimeUnit.SECONDS); // 时间轮中的槽数
        // 创建一个任务
        TimerTask task = new TimerTask() {
            @Override
            public void run(Timeout timeout) throws Exception {
                System.out.println("执行任务" + " ，执行时间：" + LocalDateTime.now());
            }
        };
        // 将任务添加到延迟队列中
        timer.newTimeout(task, 0, TimeUnit.SECONDS);
    }
    public void test(){
        System.out.println("123");
    }
}
