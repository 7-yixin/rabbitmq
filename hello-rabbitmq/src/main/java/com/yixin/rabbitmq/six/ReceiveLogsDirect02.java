package com.yixin.rabbitmq.six;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

public class ReceiveLogsDirect02 {
    public static final String EXCHANGE_NAME = "Direct-Logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
//        声明交换机
//        channel.exchangeDeclare(EXCHANGE_NAME, BuiltinExchangeType.DIRECT);
        // 声明队列
        channel.queueDeclare("disk",false,false,false,null);
        // 交换机绑定队列
        channel.queueBind("disk",EXCHANGE_NAME,"123");

        // 接受消息
        DeliverCallback deliverCallback = (d, m) ->{
            System.out.println("directlogs02控制台打印接受到的消息："+ new String(m.getBody()));
        };
        CancelCallback cancelCallback = (d) ->{};
        channel.basicConsume("disk",true,deliverCallback,cancelCallback);


    }

}
