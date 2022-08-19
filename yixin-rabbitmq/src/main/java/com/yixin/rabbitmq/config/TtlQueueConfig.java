package com.yixin.rabbitmq.config;

import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class TtlQueueConfig {
    // 普通交换机
    public static final String X_EXCHANGE = "X";
    // 死信交换机
    public static final String DEAD_EXCHANGE = "Y";
    // 普通队列QA
    public static final String QA_QUEUE = "QA";
    // 普通队列QB
    public static final String QB_QUEUE = "QB";
    public static final String QC_QUEUE = "QC";
    // 死信队列QD
    public static final String DEAD_QUEUE = "QD";

    // 声明交换机  别名
    @Bean("xExchange")
    public DirectExchange xExchange(){
        return new DirectExchange(X_EXCHANGE);
    }
    @Bean("yExchange")
    public DirectExchange yExchange(){
        return new DirectExchange(DEAD_EXCHANGE);
    }

    // 声明队列
    @Bean("queueA")
    public Queue queueA(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        arguments.put("x-message-ttl",10000);

        return QueueBuilder.durable(QA_QUEUE).withArguments(arguments).build();
    }

    @Bean("queueB")
    public Queue queueB(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        arguments.put("x-message-ttl",40000);

        return QueueBuilder.durable(QB_QUEUE).withArguments(arguments).build();
    }

    @Bean("queueC")
    public Queue queueC(){
        Map<String, Object> arguments = new HashMap<>();
        arguments.put("x-dead-letter-exchange",DEAD_EXCHANGE);
        arguments.put("x-dead-letter-routing-key","YD");
        return QueueBuilder.durable(QC_QUEUE).withArguments(arguments).build();
    }

    @Bean("queueD")
    public Queue queueD(){
        return QueueBuilder.durable(DEAD_QUEUE).build();
    }

    // 队列绑定交换机
    @Bean
    public Binding queueABindingX(@Qualifier("queueA") Queue queueA,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueA).to(xExchange).with("XA");

    }
    @Bean
    public Binding queueBBindingX(@Qualifier("queueB") Queue queueB,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueB).to(xExchange).with("XB");

    }

    @Bean
    public Binding queueCBindingX(@Qualifier("queueC") Queue queueC,
                                  @Qualifier("xExchange") DirectExchange xExchange){
        return BindingBuilder.bind(queueC).to(xExchange).with("XC");

    }
    @Bean
    public Binding queueDBindingX(@Qualifier("queueD") Queue queueD,
                                  @Qualifier("yExchange") DirectExchange yExchange){
        return BindingBuilder.bind(queueD).to(yExchange).with("YD");

    }

}
