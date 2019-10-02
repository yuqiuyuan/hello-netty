package server;

import com.sun.org.apache.xpath.internal.operations.String;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @ClassName TcpServerHandler
 * @Description Tcp服务器处理器
 * @Author drebander
 * @Date 2019-09-30 3:33 PM
 * @Version 1.0
 **/
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {

    /**
     * 从客户端接收到的消息
     * 服务器向指定客户端发送消息，只需要通过'map'将客户端的'id'和'channel'存起来
     * 在需要的时候通过'writeAndFlush'方法发送即可
     *
     * @param channelHandlerContext
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object msg) throws Exception {
        ByteBuf m = (ByteBuf) msg;
        System.out.println(ByteBufUtil.hexDump(m));
        System.out.println(m.readByte());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }
}
