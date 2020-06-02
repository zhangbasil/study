package com.zhangbin.netty.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * 使用JDK NIO
 *
 *  缺点：Read轮询要消耗CPU，如果几千上万的连接，则轮询的效率很低
 *
 *  处理：只有当连接上有数据的时候才去处理，这就是IO多路复用的来源
 */
public class NonBlockingServer {

    public static void main(String[] args) {

        singleThread();

    }

    public static void singleThread() {
        try {
            // 打开一个通道
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            // 绑定一个端口
            socketChannel.bind(new InetSocketAddress(8080));
            // 设置Accept为非阻塞
            socketChannel.configureBlocking(false);

            List<SocketChannel> clients = new ArrayList<>();
            // 一个线程一直等待接受客户端连接
            while (true) {
                SocketChannel client = socketChannel.accept();
                if (client == null) {
                    System.out.println("暂无客户端连接....");
                    Thread.sleep(2000);
                } else {
                    System.out.println("客户端连接上 port: " + client.socket().getPort());
                    // 设置Read操作也是非阻塞
                    client.configureBlocking(false);
                    clients.add(client);
                }

                for (SocketChannel channel : clients) {
                    ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5);
                    channel.read(byteBuffer);
                    byteBuffer.flip();
                    byte[] content = new byte[byteBuffer.remaining()];
                    byteBuffer.get(content);
                    System.out.println("客户端==>" + channel.socket().getPort() + " : " + new String(content));
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
