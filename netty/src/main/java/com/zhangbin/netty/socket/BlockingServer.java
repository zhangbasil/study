package com.zhangbin.netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * <p>
 * linux nc 命令连接
 * nc 192.168.0.229 8080
 */
public class BlockingServer {

    public static void main(String[] args) throws IOException {

        multiClient();

    }

    /**
     * 由于 Accept 和 Read 都是阻塞的，此方法只能连接一个客户端
     */
    private static void oneClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println(" 启动 socket server --> port: " + 8080);

            // 接收客户端 阻塞
            Socket client = serverSocket.accept();
            System.out.println(" 客户端连接成功  port : " + client.getPort());

            InputStream inputStream = client.getInputStream();
            byte[] content = new byte[1024];
            // Read 阻塞
            while (inputStream.read(content) > 0) {
                System.out.println("接受到客户端的信息：" + new String(content));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 多客户端连接
     *
     */
    private static void multiClient() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println(" 启动 socket server --> port: " + 8080);
            for (int i = 0; i < 10; i++) {
                new Thread(() -> {
                    try {
                        System.out.println(Thread.currentThread().getName() + " 进入等待接受客户端连接");
                        Socket client = serverSocket.accept();
                        System.out.println("线程 " + Thread.currentThread().getName() + " 连接成功 " + client.getPort());
                        InputStream inputStream = client.getInputStream();
                        byte[] content = new byte[1024];
                        // Read 阻塞
                        while (inputStream.read(content) > 0) {
                            System.out.println("接受到客户端的信息：" + new String(content));
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                }, "socket-" + i).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
