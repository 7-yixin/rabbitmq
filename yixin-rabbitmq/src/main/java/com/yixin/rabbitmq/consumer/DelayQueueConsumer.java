package com.yixin.rabbitmq.consumer;

import com.rabbitmq.client.MessageProperties;
import com.yixin.rabbitmq.config.DelayedQueueConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@Component
@Slf4j
public class DelayQueueConsumer {
    @RabbitListener(queues = DelayedQueueConfig.DELAYED_QUEUE_NAME)
    public void receiveDelayedQueue(Message message){
        String msg = new String(message.getBody());
        log.info("当前时间：{}，收到延迟队列的消息：{}", new Date().toString(),msg);
    }
}
