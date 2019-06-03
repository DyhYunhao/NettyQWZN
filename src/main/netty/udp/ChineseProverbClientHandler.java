package udp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-6-3
 * Description:
 */
public class ChineseProverbClientHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {

        String response = datagramPacket.content().toString(CharsetUtil.UTF_8);

        if (response.startsWith("谚语查询结果：　")){
            System.out.println(response);
            channelHandlerContext.close();
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
