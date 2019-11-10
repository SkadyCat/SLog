package com.netty.Tool;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;

public class LMessager {

    public static void sendMessage(String value, ChannelHandlerContext context){

        System.out.println("执行发送任务");
        context.channel().write(
                new TextWebSocketFrame(
                        value
                )
        );
    }
}
