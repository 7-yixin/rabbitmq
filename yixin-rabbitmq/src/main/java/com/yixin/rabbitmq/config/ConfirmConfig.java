package com.yixin.rabbitmq.config;

import com.rabbitmq.client.AMQP;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfirmConfig {
    // 确认交换机
    public static final String CONFIRM_EXCHANGE = "confirm_exchange";
    // 备份交换机
    public static final String BACKUP_EXCHANGE = "backup_exchange";
    // 备份队列
    public static final String BACKUP_QUEUE = "backup_queue";
    // 警告队列
    public static final String WARNING_QUEUE = "warning_queue";
    // 确认队列
    public static final String CONFIRM_QUEUE = "confirm_queue";
    public static final String CONFIRM_ROUTING_KEY = "confirm_routing_key";

    // 声明确认交换机并绑定备份交换机
    @Bean
    public DirectExchange confirmExchange(){
        return ExchangeBuilder.directExchange(CONFIRM_EXCHANGE)
                .durable(true)
                .withArgument("alternate-exchange",BACKUP_EXCHANGE)
                .build();
    }

    @Bean
    public FanoutExchange backupExchange(){
        return new FanoutExchange(BACKUP_EXCHANGE);
    }

    @Bean
    public Queue confirmQueue(){
        return new Queue(CONFIRM_QUEUE);
    }

    @Bean
    public Queue backupQueue(){
        return new Queue(BACKUP_QUEUE);
    }

    @Bean
    public Queue warningQueue(){
        return new Queue(WARNING_QUEUE);
    }
    @Bean
    public Binding queueToExchange(@Qualifier("confirmQueue") Queue queue,
                                   @Qualifier("confirmExchange") DirectExchange directExchange){
        return BindingBuilder.bind(queue).to(directExchange).with(CONFIRM_ROUTING_KEY);
    }

    @Bean
    public Binding backupQueueToBackupExchange(@Qualifier("backupQueue") Queue queue,
                                   @Qualifier("backupExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
    @Bean
    public Binding warningQueueToBackupExchange(@Qualifier("warningQueue") Queue queue,
                                   @Qualifier("backupExchange") FanoutExchange fanoutExchange){
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }
}
