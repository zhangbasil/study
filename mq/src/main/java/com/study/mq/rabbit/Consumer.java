package com.study.mq.rabbit;

import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@Component
public class Consumer {

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("demo_queue"))
    public void process(String message) {
        System.out.println("======>收到通知：" + message);
    }

}
