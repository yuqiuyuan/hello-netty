package client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import util.ConvertCode;

import java.time.LocalDateTime;

/**
 * @ClassName MyClientHandler
 * @Description 客户端处理
 * @Author drebander
 * @Date 2019-09-30 1:49 PM
 * @Version 1.0
 **/
public class MyClientHandler extends SimpleChannelInboundHandler<Object> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        byte[] b = new byte[m.readableBytes()];
        m.readBytes(b);
        System.out.println("server output: " + ConvertCode.receiveHexToString(b));
//        ctx.writeAndFlush("from client: " + LocalDateTime.now());
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        String massage = "680101000000220819200000";
        String s = ConvertCode.hexString2String(massage);
        ctx.writeAndFlush(s);
        massage = "68010100000022081920";
        s = ConvertCode.hexString2String(massage);
        ctx.writeAndFlush(s);
        massage = "6801010000002208";
        s = ConvertCode.hexString2String(massage);
        ctx.writeAndFlush(s);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
