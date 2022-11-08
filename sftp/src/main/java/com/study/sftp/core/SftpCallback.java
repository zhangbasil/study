package com.study.sftp.core;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
public interface SftpCallback<T> {

    T doInSftp(ChannelSftp channelSftp) throws SftpException;
}
