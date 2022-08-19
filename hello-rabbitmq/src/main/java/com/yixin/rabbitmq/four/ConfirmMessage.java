package com.yixin.rabbitmq.four;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.MessageProperties;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;

/**
 * 发布确认
 * 1.单个确认
 * 2.批量确认
 * 3.异步批量确认
 */
public class ConfirmMessage {
    public static final Integer MESSAGE_COUNT = 100;
    public static void main(String[] args) throws Exception {
//        1.单个确认
        // MessageIndividually();
//        2.批量确认
//        MessageBatch();
//        3.异步批量确认
        MessageAsync();
    }

//    单个确认
    public static void MessageIndividually() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
//        队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        // 开启发布确认
        channel.confirmSelect();        // 发布100个单独确认消息，耗时5474毫秒
        // 开始时间
        long begin = System.currentTimeMillis();
        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes());
            // 单个消息就马上进行发布确认
            boolean b = channel.waitForConfirms();
            if (b){
                System.out.println("消息发送成功 ");
            }
        }
        long ending = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+ "个单独确认消息，耗时"+(ending-begin)+"毫秒");
    }

    // 批量发布确认
    public static void MessageBatch() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
//        队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        // 开启发布确认
        channel.confirmSelect();        // 发布100个批量确认消息，耗时274毫秒
        // 开始时间
        long begin = System.currentTimeMillis();

        // 批量确认消息的大小
        int batchSize = 20;
        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes());
            // 判断达到一定的条数，批量确认一次
            if (i % batchSize == 0){
                // 发布确认
                channel.waitForConfirms();
            }
        }

        // 结束时间
        long ending = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+ "个批量确认消息，耗时"+(ending-begin)+"ms");
    }

    // 异步确认发布
    public static void MessageAsync() throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
//        队列声明
        String queueName = UUID.randomUUID().toString();
        channel.queueDeclare(queueName,true,false,false,null);
        // 开启发布确认
        channel.confirmSelect();        // 发布100个异步确认消息，耗时16ms
        // 开始时间
        long begin = System.currentTimeMillis();

        //        线程安全有序的一个哈希表，适用于高并发的情况
        ConcurrentSkipListMap<Long,String> outconfirm = new ConcurrentSkipListMap<>();

        for (Integer i = 0; i < MESSAGE_COUNT; i++) {
            String message = i + "";
            channel.basicPublish("",queueName,null,message.getBytes());
            outconfirm.put(channel.getNextPublishSeqNo(), message);

        }


        // 监听器
        // 消息确认成功
        ConfirmCallback ackCallback = (a,b) ->{
            if (b){
                ConcurrentNavigableMap<Long, String> navigableMap = outconfirm.headMap(a);
                navigableMap.clear();
            }else {
                outconfirm.remove(a);
            }
            System.out.println("确认消息：" + a);

        };
        // a 消息的标记  b 是否为批量确认
        // 消息确认失败
        ConfirmCallback nackCallback = (a,b) ->{
            System.out.println("未确认消息：" + a);
        };
        channel.addConfirmListener(ackCallback,nackCallback);
        // 结束时间
        long ending = System.currentTimeMillis();
        System.out.println("发布"+MESSAGE_COUNT+ "个异步确认消息，耗时"+(ending-begin)+"ms");
    }
}

