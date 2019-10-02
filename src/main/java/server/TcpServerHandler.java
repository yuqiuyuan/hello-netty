package server;

import com.sun.org.apache.xpath.internal.operations.String;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufUtil;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import util.ConvertCode;
import warehouse.WarehouseService;
import warehouse.impl.WarehouseServiceImpl;

/**
 * @ClassName TcpServerHandler
 * @Description Tcp服务器处理器
 * @Author drebander
 * @Date 2019-09-30 3:33 PM
 * @Version 1.0
 **/
public class TcpServerHandler extends SimpleChannelInboundHandler<Object> {
    WarehouseService warehouseService = new WarehouseServiceImpl();

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
    }

    /**
     * 从客户端接收到的消息
     * 服务器向指定客户端发送消息，只需要通过'map'将客户端的'id'和'channel'存起来
     * 在需要的时候通过'writeAndFlush'方法发送即可
     *
     * @param channel
     * @param msg
     * @throws Exception
     */
    @Override
    protected void channelRead0(ChannelHandlerContext channel, Object msg) throws Exception {
        try {
            ByteBuf buf = (ByteBuf) msg;
            byte[] bytes = new byte[buf.readableBytes()];
            //复制内容到字节数组bytes
            buf.readBytes(bytes);
            //将接收到的数据转为字符串，此字符串就是客户端发送的字符串
            java.lang.String s = ConvertCode.receiveHexToString(bytes);
            //返回16进制到客户端
//            writeToClient(receiveStr,channel,"测试");
            WarehouseService warehouseService = new WarehouseServiceImpl();
            warehouseService.add("1", channel);
            System.out.println(s);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        ctx.close();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        //删除Channel Map中的失效Client
        warehouseService.del("1");
        ctx.close();
    }


}
