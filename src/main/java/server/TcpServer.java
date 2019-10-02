package server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;

/**
 * @ClassName TcpServer
 * @Description TCP服务端实现
 * @Author drebander
 * @Date 2019-09-30 3:12 PM
 * @Version 1.0
 **/
public class TcpServer {
    private static final String IP = "127.0.0.1";
    private static final int PORT = 12340;
    /**
     * 用于分配处理业务线程的线程组个数
     */
    protected static final int BIZ_GROUP_SIZE = Runtime.getRuntime().availableProcessors() * 2;
    /**
     * 业务出现线程大小
     */
    protected static final int BIZT_HREAD_SIZE = 4;

    private static final EventLoopGroup bossGroup = new NioEventLoopGroup(BIZ_GROUP_SIZE);
    private static final EventLoopGroup workerGroup = new NioEventLoopGroup(BIZT_HREAD_SIZE);

    public static void run() throws Exception {
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new TcpServerHandler());
            }
        });
        b.bind(IP, PORT).sync();
        System.out.println("TCP服务器已启动");
    }

    protected static void shutdown() {
        workerGroup.shutdownGracefully();
        bossGroup.shutdownGracefully();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("启动TCP服务器");
        TcpServer.run();
    }
}
