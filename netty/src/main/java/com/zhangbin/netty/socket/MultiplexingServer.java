package com.zhangbin.netty.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * IO多路复用
 * <p>
 * 当多条连接共用一个阻塞对象后，进程只需要在一个阻塞对象上等待，而无须再轮询所有的连接。select epoll kqueue
 * 当某条连接有新的数据可以处理时，操作系统会通知进程，进程从阻塞状态返回，开始进行业务处理
 */
public class MultiplexingServer {

    public static void main(String[] args) {
        multiplexing();
    }


    public static void multiplexing() {
        try {
            ServerSocketChannel socketChannel = ServerSocketChannel.open();
            socketChannel.bind(new InetSocketAddress(8080));
            // 设置Accept为非阻塞
            socketChannel.configureBlocking(false);
            // 打开一个I/O多路复用器 KQueueSelector -> macOS
            Selector selector = Selector.open();
            // 将ServerSocketChannel 注册到kQueue多路复用器中，注册事件为Accept
            socketChannel.register(selector, SelectionKey.OP_ACCEPT);

            System.out.println("将ServerSocketChannel注册到多路复用器中");

            while (true) {
                System.out.println(" ===========> 将进入多路复用器的 select 方法 阻塞中......等待内核通知");
                // select 被阻塞，等待内核通知
                int select = selector.select();
                System.out.println("有 " + select + " 个事件进入");


                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = channel.accept();
                        System.out.println("客户端连接成功 port：" + client.socket().getPort() + " 进入 Accept 事件 ");
                        // 设置Read非阻塞
                        client.configureBlocking(false);

                        // 将Read事件注册到多路复用器中
                        client.register(selector, SelectionKey.OP_READ);
                    } else if (selectionKey.isReadable()) {
                        System.out.println(" 进入 Read 事件 ");
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(5);
                        if (client.read(byteBuffer) > 0) {
                            byteBuffer.flip();
                            byte[] content = new byte[byteBuffer.limit()];
                            byteBuffer.get(content);
                            System.out.println(new String(content));
                        }

                    } else {
                        System.out.println(" 进入 未知 事件 ");
                    }


                }
            }


        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
