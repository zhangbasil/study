package com.study.sftp.core.autoconfigure;

import com.study.sftp.core.SftpPool;
import com.study.sftp.core.SftpTemplate;
import com.study.sftp.core.config.ClientProperties;
import com.study.sftp.core.config.SftpPoolConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
@Configuration
@ConditionalOnClass({GenericObjectPool.class})
@EnableConfigurationProperties({ClientProperties.class})
public class SftpAutoConfiguration {


    @Bean
    @ConditionalOnMissingBean
    public SftpTemplate sftpTemplate(SftpPool sftpPool) {
        return new SftpTemplate(sftpPool);
    }

    @Bean
    @ConditionalOnMissingBean
    public SftpPool sftpPool(ClientProperties clientProperties) {
        return new SftpPool(clientProperties, sftpPoolConfig(clientProperties.getPool()));
    }

    private SftpPoolConfig sftpPoolConfig(ClientProperties.Pool pool) {
        SftpPoolConfig config = new SftpPoolConfig();
        config.setMaxTotal(pool.getMaxActive());
        config.setMaxIdle(pool.getMaxIdle());
        config.setMinIdle(pool.getMinIdle());
        return config;
    }

}
