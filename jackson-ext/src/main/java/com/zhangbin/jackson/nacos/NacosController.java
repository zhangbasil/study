package com.zhangbin.jackson.nacos;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.config.ConfigService;
import com.alibaba.nacos.api.config.listener.Listener;
import com.alibaba.nacos.api.exception.NacosException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.Executor;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
@RestController
public class NacosController {


    @GetMapping("/nacos")
    public String nacos() {


        return "";
    }

    public static void main(String[] args) throws NacosException, InterruptedException {

        String dataId = "com.maxima.tsp.contract.properties";

        String group = "DEFAULT_GROUP";


        ConfigService configService = NacosFactory.createConfigService("localhost:8848");

        configService.publishConfig(dataId, group, "");



        String content = configService.getConfig(dataId, group, 500000);

        System.out.println("content = " + content);


        configService.addListener(dataId, group, new Listener() {
            @Override
            public Executor getExecutor() {
                return null;
            }

            @Override
            public void receiveConfigInfo(String configInfo) {

                System.out.println("configInfo = " + configInfo);

            }
        });

        Thread.sleep(50000L);


    }


}
