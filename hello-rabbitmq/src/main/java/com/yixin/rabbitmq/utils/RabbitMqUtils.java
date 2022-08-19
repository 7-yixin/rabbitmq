package com.yixin.rabbitmq.utils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 连接工厂工具类
 */
public class RabbitMqUtils {

    public static Channel getChannel() throws Exception{
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
        return channel;
    }
}
