package com.study.sftp.core;

import com.study.sftp.core.config.ClientProperties;
import com.study.sftp.core.config.SftpPoolConfig;
import com.study.sftp.core.exception.SftpPoolException;
import org.apache.commons.pool2.BasePooledObjectFactory;
import org.apache.commons.pool2.PooledObject;
import org.apache.commons.pool2.impl.DefaultPooledObject;
import org.apache.commons.pool2.impl.GenericObjectPool;

import java.io.Closeable;
import java.io.IOException;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class SftpPool implements Closeable {

    protected GenericObjectPool<SftpClient> internalPool;


    public SftpPool(ClientProperties clientProperties, SftpPoolConfig sftpPoolConfig) {
        this.internalPool = new GenericObjectPool<>(new SftpClientPooledObjectFactory(clientProperties), sftpPoolConfig);
    }

    public SftpClient getResource() {
        try {
            return internalPool.borrowObject();
        } catch (Exception e) {
            throw new SftpPoolException("Could not get a resource from the pool", e);
        }
    }


    public void returnObject(SftpClient sftpClient) {
        try {
            internalPool.returnObject(sftpClient);
        } catch (Exception e) {
            throw new SftpPoolException("Could not return a resource from the pool", e);
        }
    }

    @Override
    public void close() throws IOException {
        internalPool.close();
    }


    private static class SftpClientPooledObjectFactory extends BasePooledObjectFactory<SftpClient> {
        private final ClientProperties clientProperties;

        public SftpClientPooledObjectFactory(ClientProperties clientProperties) {
            this.clientProperties = clientProperties;
        }

        @Override
        public SftpClient create() throws Exception {
            return new SftpClient(clientProperties);
        }

        @Override
        public PooledObject<SftpClient> wrap(SftpClient sftpClient) {
            return new DefaultPooledObject<>(sftpClient);
        }

        @Override
        public void destroyObject(PooledObject<SftpClient> pooledSftp) throws Exception {
            pooledSftp.getObject().disconnect();
        }

        @Override
        public boolean validateObject(PooledObject<SftpClient> pooledSftp) {
            // todo
            return super.validateObject(pooledSftp);
        }

    }
}
