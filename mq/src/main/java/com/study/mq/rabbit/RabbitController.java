package com.study.mq.rabbit;

import com.study.mq.retry.DefaultRetryListener;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.retry.ExhaustedRetryException;
import org.springframework.retry.RetryCallback;
import org.springframework.retry.backoff.FixedBackOffPolicy;
import org.springframework.retry.policy.SimpleRetryPolicy;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
@RequestMapping("/rabbit")
public class RabbitController {

    @Autowired
    private RabbitTemplate rabbitTemplate;
    @Autowired
    private RetryTemplate retryTemplate;


    @GetMapping("/produce/msg")
    public String produceMsg() {
        rabbitTemplate.convertAndSend("demo_queue", "Hello Rabbit " + LocalDateTime.now().toString());
        return "success";
    }

    @RabbitHandler
    @RabbitListener(queuesToDeclare = @Queue("demo_queue"))
    public void process(String message) {
        System.out.println("======>收到通知：" + message);
//        int a = 2 / 0;
        System.out.println("message = " + message);

    }

    @GetMapping("/retry")
    public String retry() {

        doBiz();

        retryTemplate.execute(context -> {

            doAsync();
            return "";

        });
        return "ok";
    }

    private void doBiz() {
        try {
            Thread.sleep(1000);
            System.out.println(" =====  业务逻辑执行完成 " + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    int a;

    private void doAsync() {
        a++;
        System.out.println(" =====  异步逻辑开始 " + Thread.currentThread().getName() + "  " + LocalDateTime.now());
        if (a == 6) {
            return;
        }
        int a = 2 / 0;
        System.out.println("a = " + a);
    }

    @Bean
    public RetryTemplate retryTemplate() {
        RetryTemplate retryTemplate = new RetryTemplate();
        retryTemplate.setRetryPolicy(new SimpleRetryPolicy());
        retryTemplate.setBackOffPolicy(new FixedBackOffPolicy());
        retryTemplate.registerListener(new DefaultRetryListener());
        return retryTemplate;
    }

    static class AsyncRetryTemplate extends RetryTemplate {


        public <T, E extends Throwable> T asyncExecute(RetryCallback<T, E> retryCallback) throws E, ExhaustedRetryException {
            return super.doExecute(retryCallback, null, null);
        }
    }

}
