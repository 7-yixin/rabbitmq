package com.yixin.rabbitmq.seven;

import com.rabbitmq.client.Channel;
import com.yixin.rabbitmq.utils.RabbitMqUtils;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class EmitLogsTopic {
    public static final String EXCHANGE_NAME = "Topic-Logs";

    public static void main(String[] args) throws Exception {
        Channel channel = RabbitMqUtils.getChannel();
        Map<String,String> topicMap = new HashMap<>();
        topicMap.put("quick.orange.rabbit","被队列Q102接收到");
        topicMap.put("lazy.orange.elephant","被队列a1Q2接收烈");
        topicMap.put("quick.orange.fox","被队列Q1接收到");
        topicMap.put("lazy.brown.fox","被队列Q2接收到");
        topicMap.put("lazy.pink.rabbit","虽然满足两个鄉定但只被队列a2接收一次");
        topicMap.put("quick.brown.fox","不匹配任何鄉定不会被任何队列接收到会被丢弃。");
        topicMap.put("quick.orange.male.rabbit","是四个单词不匹配任何鄉定会被丢弃");
        topicMap.put("lazy.orange.male.rabbit","是四个单词但匹配Q2");

        for (Map.Entry<String, String> stringEntry : topicMap.entrySet()) {
            String key = stringEntry.getKey();
            String value = stringEntry.getValue();

            channel.basicPublish(EXCHANGE_NAME,key,null,value.getBytes(StandardCharsets.UTF_8));
        }
    }

}
