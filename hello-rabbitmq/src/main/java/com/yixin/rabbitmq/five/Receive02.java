package com.yixin.rabbitmq.five;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

public class Receive02 {
    public static final String EXCHANGE_NAME = "logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 声明一个交换机
//        channel.exchangeDeclare(EXCHANGE_NAME,"fanout");
        // 声明一个临时队列  用完自动删除
        String queue = channel.queueDeclare().getQueue();

        // 绑定交换机和队列
        channel.queueBind(queue,EXCHANGE_NAME,"");
        System.out.println("等待接受消息，吧接受到的消息打印在屏幕上。。。。。");

        // 接受消息
        DeliverCallback deliverCallback = (d,m) ->{
            System.out.println("logs02控制台打印接受到的消息："+ new String(m.getBody()));
        };
        CancelCallback cancelCallback = (d) ->{};
        channel.basicConsume(queue,true,deliverCallback,cancelCallback);
    }
}
