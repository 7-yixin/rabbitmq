package com.yixin.rabbitmq.three;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.MessageProperties;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.util.Scanner;

public class Task02 {
    public static final String TASK_QUEUE = "task_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        // 开启发布确认
        channel.confirmSelect();
//        队列持久化第二个参数
        channel.queueDeclare(TASK_QUEUE,true,false,false,null);
        // 发消息
        // 从控制台中接收信息
        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()){
            String message = scanner.next();            // 消息持久化
            channel.basicPublish("",TASK_QUEUE, MessageProperties.PERSISTENT_TEXT_PLAIN,message.getBytes());
            System.out.println("消息发送完毕");
        }
    }
}
