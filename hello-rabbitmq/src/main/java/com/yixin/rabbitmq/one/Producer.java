package com.yixin.rabbitmq.one;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.impl.AMQBasicProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeoutException;

// 发消息，生产者
public class Producer {

//    队列名称
    public static final String QUEUE_NAME = "hello";
//    发消息
    public static void main(String[] args) throws IOException, TimeoutException {
        // 创建一个连接工厂
        ConnectionFactory factory = new ConnectionFactory();
        // 工厂IP连接Rabbit队列
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

        /**
         * 生成一个队列
         * 1.队列名称
         * 2.队列里面的消息是否持久化（磁盘），默认情况下消息存储在内存中
         * 3.该队列是否只工一个消费者进行消费，是否进行消费者共享，true可以多个消费者。false 只能一个消费者
         * 4.是否自动删除，最后一个消费者端开启连接后，该队列一句是否自动删除，true表示自动删除。false表示不自动删除
         *
          */

        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-max-priority",10);  // 官方允许是0-255之间   现在是0-10
        channel.queueDeclare(QUEUE_NAME,true,false,false,arguments);
        // 发消息

        for (int i = 1; i < 10; i++) {
            String message = "hello World ---->"+i;
            if (i == 5){
                AMQP.BasicProperties properties =
                        new AMQP.BasicProperties().builder().priority(5).build();
                channel.basicPublish("",QUEUE_NAME,properties,message.getBytes());
            }else {
                channel.basicPublish("",QUEUE_NAME,null,message.getBytes());

            }

        }
        /**
         * 发送一个消费者
         * 1.发送到哪个交换机
         * 2.路由的key值是哪个，本次队列的名称
         * 3.其他参数
         * 4.发送消息的消息体
         */
        System.out.println("消息发送完毕");
    }




}

