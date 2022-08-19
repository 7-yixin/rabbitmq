package com.yixin.rabbitmq.five;

import com.rabbitmq.client.Channel;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class EmitLogs {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        Scanner s = new Scanner(System.in);
        while (s.hasNext()){
            String message = s.next();
            channel.basicPublish(EXCHANGE_NAME,"",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("生产者发出消息：" + message);

        }
    }
}
