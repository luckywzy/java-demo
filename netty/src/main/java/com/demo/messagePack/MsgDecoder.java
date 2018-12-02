package com.demo.messagePack;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import org.msgpack.MessagePack;

import java.util.List;

public class MsgDecoder extends MessageToMessageDecoder<ByteBuf> {


    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        final int len = byteBuf.readableBytes();
        Byte[] rsv;
        rsv = new Byte[len];
        int index =byteBuf.readerIndex();
       /* byteBuf.getBytes(index, rsv, 0, len);
        MessagePack messagePack = new MessagePack();
        list.add(messagePack.read(rsv));*/
    }
}
