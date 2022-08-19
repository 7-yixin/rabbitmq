package com.yixin.rabbitmq.utils;

import java.util.concurrent.TimeUnit;

public class TimeUtils {
    public static final void sleepTime(int second) {
        try {
            TimeUnit.SECONDS.sleep(second);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
