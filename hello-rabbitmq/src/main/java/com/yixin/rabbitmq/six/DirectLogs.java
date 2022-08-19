package com.yixin.rabbitmq.six;

import com.rabbitmq.client.Channel;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class DirectLogs {
    public static final String EXCHANGE_NAME = "Direct-Logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        Scanner s = new Scanner(System.in);
        while (s.hasNext()){
            String message = s.next();
            channel.basicPublish(EXCHANGE_NAME,"123",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息：" + message);

        }
    }
}
