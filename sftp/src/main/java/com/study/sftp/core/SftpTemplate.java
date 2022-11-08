package com.study.sftp.core;

import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.SftpException;
import org.springframework.util.Assert;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

/**
 * @author <a href="mailto:bin.zhang@itiaoling.com">zhangbin</a>
 */
public class SftpTemplate {

    private final SftpPool sftpPool;

    private static final String SEPARATOR = "/";

    public SftpTemplate(SftpPool sftpPool) {
        this.sftpPool = sftpPool;
    }


    public <T> T execute(SftpCallback<T> action) {
        Assert.notNull(action, "Callback object must not be null");
        SftpClient sftpClient = null;
        try {
            sftpClient = sftpPool.getResource();
            return action.doInSftp(sftpClient.getChannelSftp());
        } catch (SftpException e) {
            throw new RuntimeException("sftp execute exception ", e);
        } finally {
            if (sftpClient != null) {
                sftpPool.returnObject(sftpClient);
            }
        }
    }


    /**
     * @param src /static/download/1.png
     * @param dst /Users/admin/Downloads/   /Users/admin/Downloads/rename.png
     */
    public void download(String src, String dst) {
        Assert.hasLength(src, "from must not be null");
        Assert.hasLength(dst, "to must not be null");
        if (src.endsWith(SEPARATOR)) {
            throw new RuntimeException("not supported to get directory " + src);
        }
        if (!exits(src)) {
            throw new RuntimeException("remote file '" + src + "' not exists.");
        }
        execute(channelSftp -> {
            channelSftp.get(src, dst);
            return true;
        });
    }

    public byte[] download(String src) {
        Assert.hasLength(src, "from must not be null");
        if (!exits(src)) {
            throw new RuntimeException("remote file '" + src + "' not exists.");
        }
        return execute(channelSftp -> {
            try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
                channelSftp.get(src, outputStream);
                return outputStream.toByteArray();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }

    public void upload(String src, String dst) {
        Assert.hasLength(src, "from must not be null");
        Assert.hasLength(dst, "to must not be null");

        if (src.endsWith(SEPARATOR)) {
            throw new RuntimeException("'" + src + "' is not a file.");
        }

        if (!new File(src).exists()) {
            throw new RuntimeException("local file '" + src + "' not exits.");
        }

        String path = dst.substring(0, dst.lastIndexOf(SEPARATOR) + 1);
        if (!"".equals(path)) {
            cdAndMkdir(path);
        }
        execute(channelSftp -> {
            channelSftp.put(src, dst);
            return true;
        });
    }

    public void upload(byte[] src, String dst) {
        Assert.hasLength(dst, "to must not be null");
        String path = dst.substring(0, dst.lastIndexOf(SEPARATOR) + 1);
        if (!"".equals(path)) {
            cdAndMkdir(path);
        }
        execute(channelSftp -> {
            try (ByteArrayInputStream inputStream = new ByteArrayInputStream(src)) {
                channelSftp.put(inputStream, dst);
                return true;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }


    public void mkdir(String path) {
        execute(channelSftp -> {
            channelSftp.mkdir(path);
            return true;
        });
    }

    public void cdAndMkdir(String path) {
        if (cd(path)) {
            return;
        }
        if (path.startsWith(SEPARATOR)) {
            cd(SEPARATOR);
        }
        String[] dirs = path.split(SEPARATOR);
        for (String dir : dirs) {
            if (Objects.equals("", dir)) {
                continue;
            } else if (!isDir(dir)) {
                mkdir(dir);
            }
            cd(dir);
        }
    }

    public boolean cd(String path) {
        return execute(channelSftp -> {
            try {
                channelSftp.cd(path);
                return true;
            } catch (SftpException e) {
                return false;
            }
        });
    }

    public boolean isDir(String path) {
        return execute(channelSftp -> {
            try {
                return channelSftp.lstat(path).isDir();
            } catch (SftpException e) {
                if (e.id == ChannelSftp.SSH_FX_NO_SUCH_FILE) {
                    return false;
                }
                throw new SftpException(e.id, "cannot check status for dir '" + path + "'." + e.getMessage(), e.getCause());
            }
        });
    }

    public boolean exits(String path) {
        return execute(channelSftp -> {
            try {
                channelSftp.lstat(path);
                return true;
            } catch (SftpException e) {
                return e.id != ChannelSftp.SSH_FX_NO_SUCH_FILE;
            }
        });
    }
}
