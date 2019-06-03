package udp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioDatagramChannel;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-6-3
 * Description:
 */
public class ChineseProverbServer {

    public void run(int port) throws InterruptedException {
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioDatagramChannel.class)
             .option(ChannelOption.SO_BROADCAST, true)
             .handler(new ChineseProverbServerHandler());
            b.bind(port).sync().channel().closeFuture().await();
        } finally {
            group.shutdownGracefully();
        }
    }

    public static void main(String[] args) throws Exception{
        int port = 8080;
        if (args != null && args.length > 0){
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }

        new ChineseProverbServer().run(port);
    }

}

