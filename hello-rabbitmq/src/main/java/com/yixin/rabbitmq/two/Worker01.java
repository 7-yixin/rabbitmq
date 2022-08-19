package com.yixin.rabbitmq.two;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

public class Worker01 {
    public static final String QUEUE_NAME = "hello";
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        // 声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            System.out.println(new String(message.getBody()));
        };

        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        System.out.println("C2 等待接收消息");
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}
