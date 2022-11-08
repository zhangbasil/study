package com.study.sftp.core;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import com.study.sftp.core.config.ClientProperties;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class SftpClient {

    private final ChannelSftp channelSftp;

    private final Session session;


    public SftpClient(ClientProperties clientProperties) {
        try {
            JSch jsch = new JSch();
            session = jsch.getSession(clientProperties.getUsername(), clientProperties.getHost(), clientProperties.getPort());
            session.setConfig("StrictHostKeyChecking", "no");
            session.setPassword(clientProperties.getPassword());
            session.connect(clientProperties.getConnectTimeout());
            channelSftp = (ChannelSftp) session.openChannel("sftp");
            channelSftp.connect();
            System.out.println(" =====>>> ");
        } catch (Exception e) {
            disconnect();
            throw new IllegalStateException("failed to create sftp Client", e);
        }
    }


    public void disconnect() {
        if (session != null) {
            session.disconnect();
        }
    }

    public ChannelSftp getChannelSftp() {
        return channelSftp;
    }
}
