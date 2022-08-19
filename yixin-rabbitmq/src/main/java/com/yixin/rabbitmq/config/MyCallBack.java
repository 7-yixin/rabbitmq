package com.yixin.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.ReturnedMessage;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
@Slf4j
public class MyCallBack implements RabbitTemplate.ConfirmCallback,RabbitTemplate.ReturnsCallback {

//    将该类注入RabbitTemplate.ConfirmCallback并实例化

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @PostConstruct
    public void init(){
        rabbitTemplate.setConfirmCallback(this);
        rabbitTemplate.setReturnsCallback(this);
    }
    /**
     * 交换机确认回调方法
     * 1.发消息  交换机接收到了 回调
     *  1.1 correlationData 保存回调消息的ID及相关信息
     *  1.2 交换机收到消息  ack = true
     *  1.3 cause null
     * 2.发消息 交换机 接受失败了 回调
     *  2.1 correlationData 保存回调消息ID及相关信息
     *  2.2交换机收到消息， ack = false
     *  2.3 交换机失败的原因
     *
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        String id = correlationData != null ? correlationData.getId() : "" ;
        if (b){
            log.info("交换机已经收到ID为：{} 的消息",id);
        }else {
            log.info("交换机还没有收到ID为：{} 的消息,原因：{}",id,s);
        }
    }

//    队列回调
    @Override
    public void returnedMessage(ReturnedMessage returnedMessage) {
        log.info("消息：{}，被交换机：{}退回，退回原因：{}，路由Key：{}",
                returnedMessage.getMessage(),
                returnedMessage.getExchange(),
                returnedMessage.getReplyText(),
                returnedMessage.getRoutingKey());
    }
}
