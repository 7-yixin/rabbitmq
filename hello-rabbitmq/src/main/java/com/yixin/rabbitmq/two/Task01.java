package com.yixin.rabbitmq.two;

import com.rabbitmq.client.Channel;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.util.Scanner;

public class Task01 {
    public static final String QUEUE_NAME = "hello";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.queueDeclare(QUEUE_NAME,false,false,false,null);
        // 发消息
        // 从控制台中接收信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();
            channel.basicPublish("",QUEUE_NAME,null,message.getBytes());
            System.out.println("消息发送完毕");
        }
    }
}
