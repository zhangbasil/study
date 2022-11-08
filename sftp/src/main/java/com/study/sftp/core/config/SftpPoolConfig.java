package com.study.sftp.core.config;

import com.study.sftp.core.SftpClient;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class SftpPoolConfig extends GenericObjectPoolConfig<SftpClient> {

    public SftpPoolConfig() {
        // defaults to make your life with connection pool easier :)
        setTestWhileIdle(true);
        setMinEvictableIdleTimeMillis(60000);
        setTimeBetweenEvictionRunsMillis(30000);
        setNumTestsPerEvictionRun(-1);
    }

}
