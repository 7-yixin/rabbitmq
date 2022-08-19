package com.yixin.rabbitmq.three;

import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yixin.rabbitmq.utils.RabbitMqUtils;
import com.yixin.rabbitmq.utils.TimeUtils;

import java.util.concurrent.TimeUnit;

public class Work03 {
    public static final String TASK_QUEUE = "task_queue";
    public static void main(String[] args) throws Exception {

        Channel channel = RabbitMqUtils.getChannel();
        // 声明接收消息
        DeliverCallback deliverCallback = (consumerTag, message) -> {
            TimeUtils.sleepTime(2);
            System.out.println("接收到消息"+new String(message.getBody()));
            /**
             * 1.消息的标记tag
             * 2.是否进行批量回答，一般是false
             */
            channel.basicAck(message.getEnvelope().getDeliveryTag(),false);
        };
//        不公平分发原则
//        int prefechCount = 1;
        int prefetchCount = 2;
        channel.basicQos(prefetchCount);
        // 取消消息时的回调
        CancelCallback cancelCallback = consumerTag -> {
            System.out.println("消息消费被中断");
        };
        System.out.println("C3 等待接收消息");
        boolean autoAck = false;
        channel.basicConsume(TASK_QUEUE,autoAck,deliverCallback,cancelCallback);

    }
}
