package com.zhangbin.netty.socket;

import java.nio.ByteBuffer;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 *
 * JDK NIO byteBuffer 不足
 * ① ByteBuffer 长度固定，一旦分配完成，它的容量不能动态扩展和收缩，当需要编码的POJO对象大于ByteBuffer的容量时，会发生索引越界异常(BufferOverflowException)
 * ② ByteBuffer 只有一个标识位置的指针 position，读写的时候需要手动调用 flip() 和 rewind() 等，很容易导致程序处理是不
 *
 */
public class ByteBufferDemo {

    public static void main(String[] args) {
        case2();

    }

    private static void case1() {
        String value = "Netty";
        ByteBuffer byteBuffer = ByteBuffer.allocate(2);
        byteBuffer.put(value.getBytes());
    }

    private static void case2() {
        String value = "JDK NIO byteBuffer";
        ByteBuffer byteBuffer = ByteBuffer.allocate(20);
        byteBuffer.put(value.getBytes());
        byteBuffer.flip();

//        byte[] content = new byte[byteBuffer.remaining()];
        byte[] content = new byte[byteBuffer.limit()];
        byteBuffer.get(content);

        System.out.println("content = " + new String(content));

    }

}
