package com.yixin.rabbitmq.controller;

import com.yixin.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class ProducerController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    //开始发消息，  测试确认
    @GetMapping("/sendConfirmMsg/{message}")
    public void sendConfirmMsg(@PathVariable String message){
        CorrelationData correlationData = new CorrelationData("1");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,
                ConfirmConfig.CONFIRM_ROUTING_KEY,
                message,correlationData);
        log.info("发送消息内容：{}",message);

        CorrelationData correlationData2 = new CorrelationData("2");
        rabbitTemplate.convertAndSend(ConfirmConfig.CONFIRM_EXCHANGE,
                ConfirmConfig.CONFIRM_ROUTING_KEY+1,
                message,correlationData2);
        log.info("发送消息内容：{}",message);
    }

}
