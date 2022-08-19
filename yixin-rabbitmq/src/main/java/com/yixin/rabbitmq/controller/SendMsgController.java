package com.yixin.rabbitmq.controller;

import com.yixin.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/ttl")
@Slf4j
public class SendMsgController {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @GetMapping("/sendMsg/{message}")
    public void sendMsg(@PathVariable("message") String message){
        log.info("当前时间：{}，发送一条消息给两个TTL队列：{}", new Date().toString(),message);
        rabbitTemplate.convertAndSend("X","XA","消息来自ttl为10ms队列"+message);
        rabbitTemplate.convertAndSend("X","XB","消息来自ttl为40ms队列"+message);

    }

    @GetMapping("/sendExpirationMsg/{message}/{ttlTime}")
    public void sendMsg(@PathVariable String message,@PathVariable String ttlTime){
        log.info("当前时间：{}，发送一条时长{}ms消息给队列QC：{}", new Date().toString(),ttlTime,message);
        rabbitTemplate.convertAndSend("X","XC",message,msg -> {
            msg.getMessageProperties().setExpiration(ttlTime);
            return msg;
        });
    }

    @GetMapping("/sendDelayedMsg/{message}/{delayedTime}")
    public void sendMsg(@PathVariable String message,@PathVariable Integer delayedTime){
        log.info("当前时间：{}，发送一条时长{}ms消息给delayed队列：{}", new Date().toString(),delayedTime,message);
        rabbitTemplate.convertAndSend(DelayedQueueConfig.DELAYED_EXCHANGE_NAME,
                DelayedQueueConfig.DELAYED_ROUTING_KEY,message, msg -> {
            msg.getMessageProperties().setDelay(delayedTime);
            return msg;
        });
    }


}
