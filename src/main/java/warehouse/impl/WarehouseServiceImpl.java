package warehouse.impl;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import util.ConvertCode;
import warehouse.WarehouseService;

/**
 * @ClassName WarehouseServiceImpl
 * @Description TODO
 * @Author drebander
 * @Date 2019-10-02 4:21 PM
 * @Version 1.0
 **/
public class WarehouseServiceImpl implements WarehouseService {
    @Override
    public void add(String connectorId, ChannelHandlerContext ctx) {
        warehouse.put(connectorId, ctx);
    }

    @Override
    public ChannelHandlerContext get(String connectorId) {
        return warehouse.get(connectorId);
    }

    @Override
    public void del(String connectorId) {
        warehouse.remove(connectorId);
    }

    @Override
    public int getSize() {
        return warehouse.size();
    }

    @Override
    public void send(String connectorId, String message) {
        ChannelHandlerContext channel = warehouse.get(connectorId);
        try {
            byte[] bytes = ConvertCode.hexString2Bytes(message);
            //netty需要用ByteBuf传输
            ByteBuf byteBuf = channel.alloc().buffer(bytes.length);
            //对接需要16进制
            byteBuf.writeBytes(bytes);
            channel.writeAndFlush(byteBuf);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
