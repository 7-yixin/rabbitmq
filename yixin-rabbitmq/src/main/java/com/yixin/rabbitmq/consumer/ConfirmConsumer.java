package com.yixin.rabbitmq.consumer;

import com.yixin.rabbitmq.config.ConfirmConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class ConfirmConsumer {

    @RabbitListener(queues = ConfirmConfig.CONFIRM_QUEUE)
    public void receiveConfirm(Message message){
        String msg = new String(message.getBody());
        log.info("接受到的队列confirm.queue消息：{}",msg);
    }
}
