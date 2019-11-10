package com.netty.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;


public class NettyDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf buf, List<Object> list) throws Exception {
		list.add(buf.readBytes(buf.readableBytes()));
	}

	public static byte[] intToBytes2(int value)
	{
		    byte[] src = new byte[4];
		    src[0] = (byte) ((value>>24) & 0xFF);
		    src[1] = (byte) ((value>>16)& 0xFF);
		    src[2] = (byte) ((value>>8)&0xFF);
		    src[3] = (byte) (value & 0xFF);
		    return src;
		}

	public static int bytesToInt(byte[] src, int offset) {
		int value;

		value = (int) ((src[offset] & 0xFF)

				| ((src[offset+1] & 0xFF)<<8)

				| ((src[offset+2] & 0xFF)<<16)

				| ((src[offset+3] & 0xFF)<<24));

		return value;
	}

	public static float getFloat(byte[] b) {
		int accum = 0;
		accum = accum|(b[0] & 0xff) << 0;
		accum = accum|(b[1] & 0xff) << 8;
		accum = accum|(b[2] & 0xff) << 16;
		accum = accum|(b[3] & 0xff) << 24;
		System.out.println(accum);
		return Float.intBitsToFloat(accum);
	}


}
