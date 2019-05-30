package netty_serializable;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-29
 * Description:
 */
public class SubReqServer {
    public void bind(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) throws Exception {
                            socketChannel.pipeline().addLast(
                                    //weakCachingConcurrentResolver创建线程安全的WeakReferenceMap对类加载器进行缓存,
                                    // 支持多线程并发访问,当虚拟机内存不足时会释放缓存中的内存防止内存泄露
                                new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(
                                        this.getClass().getClassLoader())));
                            socketChannel.pipeline().addLast(new ObjectEncoder());
                            socketChannel.pipeline().addLast(new SubReqServerHandler());
                        }
                    });

            ChannelFuture f = b.bind(port).sync();
            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8080;
        if (args != null && args.length > 0){
            port = Integer.valueOf(args[0]);
        }

        new SubReqServer().bind(port);
    }
}

@ChannelHandler.Sharable
class SubReqServerHandler extends ChannelInboundHandlerAdapter {

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        SubscribeReq req = (SubscribeReq) msg;
        if ("daiyh".equalsIgnoreCase(req.getUserName())){
            System.out.println("Service accept client req: [" + req.toString() + "]");
            ctx.writeAndFlush(resp(req.getSubReqID()));
        }
    }

    private SubscribeResp resp(int subReqID){
        SubscribeResp resp = new SubscribeResp();
        resp.setSubReqID(subReqID);
        resp.setRespCode(0);
        resp.setDesc("Netty order succeed");
        return resp;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
