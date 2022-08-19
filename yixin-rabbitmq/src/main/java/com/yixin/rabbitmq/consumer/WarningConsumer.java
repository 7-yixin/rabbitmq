package com.yixin.rabbitmq.consumer;

import com.yixin.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WarningConsumer {

    @RabbitListener(queues = ConfirmConfig.BACKUP_QUEUE)
    public void receiveWarning(Message message){
        String msg = new String(message.getBody());
        log.error("报警发现不可路由消息：{}",msg);
    }
}
