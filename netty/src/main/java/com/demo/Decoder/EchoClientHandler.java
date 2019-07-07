package com.demo.Decoder;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@NoArgsConstructor
public class EchoClientHandler extends ChannelHandlerAdapter {

	static final String QUE = "HI, welcome to netty！！！$_";
	private int counter = 0;


	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {

		for (int i = 0; i < 10; i++) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(QUE.getBytes()));
		}

	}

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		log.info("count :[" + (++counter) + "]" + "rsv:[" + body + "]" + " len:[ " + body.length() + " ]");
	}

	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
}
