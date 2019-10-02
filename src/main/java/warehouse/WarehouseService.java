package warehouse;

import io.netty.channel.ChannelHandlerContext;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @ClassName WarehouseService
 * @Description TODO
 * @Author drebander
 * @Date 2019-10-02 4:19 PM
 * @Version 1.0
 **/
public interface WarehouseService {
    Map<String, ChannelHandlerContext> warehouse = new ConcurrentHashMap<>();

    void add(String connectorId, ChannelHandlerContext ctx);

    ChannelHandlerContext get(String connectorId);

    void del(String connectorId);

    int getSize();

    void send(String connectorId, String message);
}
