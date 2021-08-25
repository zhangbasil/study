package com.zhangbin.tool.mq;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;

import java.util.Timer;
import java.util.TimerTask;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class RocketMQ {


    public static void main(String[] args) throws MQClientException, RemotingException, InterruptedException, MQBrokerException {

        rocketMQProduce();
    }



    private static void rocketMQProduce() throws MQClientException, RemotingException, InterruptedException, MQBrokerException {
        DefaultMQProducer producer = new DefaultMQProducer("group_zhangbin");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();

        Message message = new Message("topic_zhangbin", "GGGGGGGGGGGG".getBytes());
        SendResult result = producer.send(message);
        producer.send(message);

        System.out.println("result = " + result);
    }



}
