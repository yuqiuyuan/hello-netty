package server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.UUID;

/**
 * @ClassName ServerHandler
 * @Description 服务实际工作者
 * @Author drebander
 * @Date 2019-09-29 6:20 PM
 * @Version 1.0
 **/
public class ServerHandler extends SimpleChannelInboundHandler<String> {


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        System.out.println(String.format("%s,%s", ctx.channel().remoteAddress(), msg));
        ctx.channel().writeAndFlush(String.format("from server: %s", UUID.randomUUID()));
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
