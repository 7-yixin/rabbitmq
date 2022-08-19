package com.yixin.rabbitmq.eight;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.util.HashMap;
import java.util.Map;

// 测试死信队列的消费者1
public class Consumer01 {
    public static final String NORMAL_EXCHANGE = "normal_exchange";
    public static final String DEAD_EXCHANGE = "dead_exchange";
    public static final String NORMAL_QUEUE = "normal_queue";
    public static final String DEAD_QUEUE = "dead_queue";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();

        // 声明交换机
        channel.exchangeDeclare(NORMAL_EXCHANGE, BuiltinExchangeType.DIRECT);
        channel.exchangeDeclare(DEAD_EXCHANGE, BuiltinExchangeType.DIRECT);

        Map<String, Object> arguments = new HashMap<>();
        // 设置死信交换机
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        // 设置死信队列
        arguments.put("x-dead-letter-routing-key","lisi");
//        arguments.put("x-max-length",6);

        // 声明队列
        channel.queueDeclare(NORMAL_QUEUE,false,false,false,arguments);
        channel.queueDeclare(DEAD_QUEUE,false,false,false,null);

        // 绑定队列与交换机
        channel.queueBind(NORMAL_QUEUE,NORMAL_EXCHANGE,"zhangsan");
        channel.queueBind(DEAD_QUEUE,DEAD_EXCHANGE,"lisi");

        DeliverCallback deliverCallback = (d,m) -> {
            String s = new String(m.getBody());
            if (s.equals("info5")) {
                System.out.println("Consumer02：：：" + new String(m.getBody()) + "该消息被c1拒绝接收");
                channel.basicReject(m.getEnvelope().getDeliveryTag(),false);
            }else {
                System.out.println("Consumer02：：：" + new String(m.getBody()));
                channel.basicAck(m.getEnvelope().getDeliveryTag(),false);


            }
        };
        CancelCallback cancelCallback = (d) -> {

        };
        // 接收消息
        channel.basicConsume(NORMAL_QUEUE,false,deliverCallback,cancelCallback);
    }
}
