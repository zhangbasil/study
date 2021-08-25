package com.zhangbin.concurrency.mq;

import org.apache.commons.lang3.StringUtils;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;


/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RequestMapping("/mq")
@RestController
public class RocketMqController {

    static DefaultMQProducer producer = new DefaultMQProducer("group_zhangbin");

    static DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("group_zhangbin");

    final static String NAME_SERVER_ADDR = "127.0.0.1:9876";

    final static String TOPIC = "topic_zhangbin";

    static {
        producer.setNamesrvAddr(NAME_SERVER_ADDR);
        consumer.setNamesrvAddr(NAME_SERVER_ADDR);
        try {
            producer.start();
            consume();
        } catch (MQClientException e) {
            System.out.println("异常：====> " + e);
            e.printStackTrace();
        }
    }


    @GetMapping("/send")
    public SendResult sendMessage(@RequestParam(required = false) String content) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        if (StringUtils.isBlank(content)) {
            content = LocalDateTime.now().toString();
        }
        Message msg = new Message(TOPIC, content.getBytes());
        if (content.startsWith("d")) {
            msg.setDelayTimeLevel(5);
        }
        return producer.send(msg);
    }

    private static void consume() throws MQClientException {
        consumer.subscribe(TOPIC, "*");
        consumer.registerMessageListener((MessageListenerConcurrently) (msgs, context) -> {
            System.out.println("=====================");
            msgs.forEach(messageExt -> System.out.println(new String(messageExt.getBody())));
            System.out.printf("%s =====> 消费消息 : %s %n", Thread.currentThread().getName(), msgs);
            return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
        });
        consumer.start();
    }

}
