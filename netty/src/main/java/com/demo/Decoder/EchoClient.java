package com.demo.Decoder;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {

    public static void main(String[] args) throws Exception {
        int port = 9191;
        new EchoClient().connect(port, "127.0.0.1");
    }

    public void connect(int port, String host) throws Exception {
        NioEventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel channel) throws Exception {
                            /**
                             * 使用特定字符解码器
                             */
                            /*ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());
                            channel.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));*/
                            /**
                             * 使用固定长度解码器
                             */
                            channel.pipeline().addLast(new FixedLengthFrameDecoder(20));
                            channel.pipeline().addLast(new StringDecoder());
                            channel.pipeline().addLast(new EchoClientHandler());

                        }
                    });
            ChannelFuture future = b.connect(host, port).sync();

            future.channel().closeFuture().sync();
        } finally {
            group.shutdownGracefully();
        }
    }
}
