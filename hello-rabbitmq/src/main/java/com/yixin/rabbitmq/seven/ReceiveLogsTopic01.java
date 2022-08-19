package com.yixin.rabbitmq.seven;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

public class ReceiveLogsTopic01 {
    public static final String EXCHANGE_NAME = "Topic-Logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.TOPIC);
        String queueName = "Q1";
        channel.queueDeclare(queueName,false,false,false,null);
        channel.queueBind(queueName,EXCHANGE_NAME,"*.orange.*");
        // 接受消息
        DeliverCallback deliverCallback = (d, m) ->{
            System.out.println("topic_logs01控制台打印接受到的消息："+ new String(m.getBody()));
        };
        CancelCallback cancelCallback = (d) ->{};
        channel.basicConsume(queueName,true,deliverCallback,cancelCallback);
    }
}
