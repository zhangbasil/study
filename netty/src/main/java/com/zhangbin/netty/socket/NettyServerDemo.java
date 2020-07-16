package com.zhangbin.netty.socket;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

import java.net.SocketAddress;
import java.util.Date;

/**
 * @author <a href="mailto:hbsy_zhb@163.com">zhangbin</a>
 * NIO
 *
 * 线程模型 reactor
 * idle监测（dubbo 心跳）
 * byteBuf  （内存池）
 * io多路复用 select pool epoll kqueue（mac）
 * 直接内存（堆外层） 零拷贝
 * 粘包半包（固定长度，分隔符，固定长度存字段）
 *
 */
public class NettyServerDemo {

    public static void main(String[] args) {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        ServerBootstrap serverBootstrap = new ServerBootstrap();
        serverBootstrap.group(bossGroup, workerGroup)
                .channel(NioServerSocketChannel.class) //初始化NIO ServerSocketChannel 并且设置 为非阻塞
//                .option(ChannelOption.SO_REUSEADDR, true)
//                .childOption(ChannelOption.TCP_NODELAY, true)
                .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ch.pipeline().addLast(new StringDecoder())
                                .addLast(new IdleStateHandler(0, 0, 3))
                                .addLast(new StringEncoder())
                                .addLast(new OperationHandler());
                    }
                });
        serverBootstrap.bind(8080);


    }


    static class OperationHandler extends ChannelDuplexHandler {


        @Override
        public void channelActive(ChannelHandlerContext ctx) throws Exception {
            super.channelActive(ctx);

            Channel channel = ctx.channel();
            SocketAddress socketAddress = channel.remoteAddress();

            System.out.println("socketAddress = " + socketAddress);

        }

        @Override
        public void channelInactive(ChannelHandlerContext ctx) throws Exception {
            super.channelInactive(ctx);
        }

        @Override
        public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
            super.channelReadComplete(ctx);
        }

        // netty idle监测 通过 IdleStateHandler 设置，空闲发送心跳
        @Override
        public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
            super.userEventTriggered(ctx, evt);
            if (evt instanceof IdleStateEvent) {
                System.out.println("new Date() = " + new Date());
//                ctx.channel().close();
                ctx.channel().writeAndFlush("heartBeat");
            }
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
            super.channelRead(ctx, msg);
            System.out.println("read -> msg = " + msg);
            ctx.channel().writeAndFlush(msg);
        }

        @Override
        public void write(ChannelHandlerContext ctx, Object msg, ChannelPromise promise) throws Exception {
            System.out.println("write -> msg = " + msg);
            super.write(ctx, msg, promise);
        }

    }


}
