package com.yixin.rabbitmq.eight;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BasicProperties;
import com.rabbitmq.client.Channel;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;

public class Producer {
    public static final String NORMAL_EXCHANGE = "normal_exchange";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
//        AMQP.BasicProperties properties =
//                new AMQP.BasicProperties()
//                        .builder().expiration("10000").build();

        for (int i = 1; i < 11; i++) {
            String message = "info" + i;
            channel.basicPublish(NORMAL_EXCHANGE,"zhangsan",null,message.getBytes(StandardCharsets.UTF_8));
            System.out.println("发送完毕");
        }
    }
}
