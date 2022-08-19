package com.yixin.rabbitmq.one;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

//消费者
public class Consumer {
    // 队列名称
    public static final String QUEUE_NAME = "hello";
//    接收消息
    public static void main(String[] args) throws IOException, TimeoutException {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("120.46.188.187");
        // 用户名
        factory.setUsername("guest");
//        factory.setVirtualHost("/");
        // 密码
        factory.setPassword("guest");
//        创建连接
        Connection connection = factory.newConnection();
        // 获取信道
        Channel channel = connection.createChannel();

        // 声明接收消息
        DeliverCallback deliverCallback = (consumerTag,message) -> {
            System.out.println(new String(message.getBody()));
        };

        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        /**
         * 消费者消费信息
         * 1.消费哪个队列
         * 2.消费成功后是否要自动应答，true 代表自动应答，  false 是手动应答
         * 3.消费者为成功消费的回调
         * 4,。消费者取录消费的回调
         */
        channel.basicConsume(QUEUE_NAME,true,deliverCallback,cancelCallback);

    }
}

