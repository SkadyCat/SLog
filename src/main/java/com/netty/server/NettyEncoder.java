package com.netty.server;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.MessageToByteEncoder;

import java.util.List;


public class NettyEncoder extends MessageToByteEncoder<byte[]> {
	
	@Override
	protected void encode(ChannelHandlerContext ctx, byte[] bytes, ByteBuf buf) throws Exception {
		buf.writeBytes(bytes);
	}

}
