package com.study.sftp.core.exception;

public class SftpPoolException extends RuntimeException {
  public SftpPoolException(String message) {
    super(message);
  }

  public SftpPoolException(Throwable e) {
    super(e);
  }

  public SftpPoolException(String message, Throwable cause) {
    super(message, cause);
  }
}
