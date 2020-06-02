package com.zhangbin.netty.socket;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * IO多路复用多线程 主从模型
 * 一个boss 两个 worker
 */
public class MultiplexingMultiThreadServer {


    private static Selector bossSelector;
    private static Selector workerSelector1;

    static ExecutorService executorService = Executors.newFixedThreadPool(3);


    public static void main(String[] args) {


        new Thread(() -> {

            handle(bossSelector);


        }, "boss").start();


    }


    static {
        try {
            bossSelector = Selector.open();
            workerSelector1 = Selector.open();
            ServerSocketChannel serverChannel = ServerSocketChannel.open();
            serverChannel.bind(new InetSocketAddress(8080));
            serverChannel.configureBlocking(false);
            serverChannel.register(bossSelector, SelectionKey.OP_ACCEPT);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void handle(Selector selector) {

        for (; ; ) {
            try {
                System.out.println(Thread.currentThread().getName() + "   阻塞");
                int select = selector.select();
                System.out.println("有 " + select + " 个事件进入");

                Set<SelectionKey> selectionKeys = selector.selectedKeys();

                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                while (iterator.hasNext()) {
                    SelectionKey selectionKey = iterator.next();
                    iterator.remove();
                    if (selectionKey.isAcceptable()) {
                        System.out.println(Thread.currentThread().getName());
                        ServerSocketChannel channel = (ServerSocketChannel) selectionKey.channel();
                        SocketChannel client = channel.accept();
                        client.configureBlocking(false);


                        executorService.execute(() -> {
                            try {
                                client.register(workerSelector1, SelectionKey.OP_READ);
                            } catch (ClosedChannelException e) {
                                e.printStackTrace();
                            }

                            handle(workerSelector1);
                        });


                    }
                    if (selectionKey.isReadable()) {
                        System.out.println(Thread.currentThread().getName());
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(120);
                        int read = client.read(byteBuffer);
                        if (read > 0) {
                            byteBuffer.flip();
                            byte[] content = new byte[byteBuffer.limit()];
                            byteBuffer.get(content);
                            System.out.println(Thread.currentThread().getName() + " : " + new String(content));
                        } else if (read == -1) { // client 关闭
                            client.close();
                        }
                    }

                }
            } catch (Exception e) {

            }

        }

    }

}
