package com.zhangbin.netty.socket;

import java.io.IOException;
import java.io.InputStream;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 */
public class Server {


    public static void main(String[] args) {

//        blockIO();

        NIO();
    }


    private static void NIO() {
        List<SocketChannel> clients = new ArrayList<>();
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress(8080));
            socketChannel.configureBlocking(false);
            while (true) {
                Thread.sleep(2000);
                SocketChannel client = socketChannel.accept();// 非阻塞
                if (Objects.isNull(client)) {
                    System.out.println(" 没有客户端连接 ");
                } else {
                    System.out.println(Thread.currentThread().getName() + " 客户端连接成功 = " + client.getRemoteAddress());
                    // 设置Read非阻塞
//                    client.configureBlocking(false);
                    clients.add(client);
                }

                for (SocketChannel channel : clients) {
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1034);
                    if (channel.read(byteBuffer) > 0) {
                        byteBuffer.flip();
                        byte[] content = new byte[byteBuffer.limit()];
                        byteBuffer.get(content);
                        System.out.println(new String(content));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void blockIO() {
        try {
            ServerSocket serverSocket = new ServerSocket(8080);
            System.out.println(" 启动 socket server --> port: " + 8080);
            // 接受客户端的连接 阻塞, 同时支持8个用户在线
            for (int i = 1; i < 9; i++) {
                new Thread(() -> {
                    Socket client = null;
                    try {
                        System.out.println(" ======= 线程：" + Thread.currentThread().getName() + "  进入Accept阻塞  ===== ");
                        client = serverSocket.accept();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    System.out.println("Client Port ====>> " + client.getPort());

                    read(client);
                }, "socket-" + i).start();

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private static void read(Socket client) {
        try {
            byte[] content = new byte[3];
            // 读取客户端的流信息
            InputStream inputStream = client.getInputStream();
            // read 操作也是阻塞
            System.out.println(" ======= 线程：" + Thread.currentThread().getName() + "  进入Read阻塞  ===== ");
            while (inputStream.read(content) != -1) {
                System.out.print(Thread.currentThread().getName() + " ===>>  " + new String(content));
            }
            System.out.println(" = " + client);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
