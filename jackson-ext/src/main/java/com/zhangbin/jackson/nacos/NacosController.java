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
 *
 * version 1.x 请求 HTTP 的方式
 *
 * namespace 就是 tenant
 *
 * raft 算法
 *
 * http://thesecretlivesofdata.com/raft/
 * https://web.stanford.edu/~ouster/cgi-bin/papers/raft-atc14
 *
 * node 三种状态 follower candidate leader
 *
 * 初始化所有的节点都是 follower 状态
 *
 * 如果follower 没有收到 leader 心跳 状态就会变成 condidate
 *
 * condidate 发起leader选举  选举过半状态变成leader 其他的condidate 变成follower
 *
 * 所有的变更操作都要通过leader来操作  leader 通过log replicate 通知所有的follower 才做数据commit
 *
 *
 * leader选举过程
 *
 *
 * version 2.x grpc
 *
 *
 *
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

//        configService.publishConfig(dataId, group, "");



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
