package udp;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.DatagramPacket;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ThreadLocalRandom;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-6-3
 * Description:
 */
public class ChineseProverbServerHandler extends SimpleChannelInboundHandler<DatagramPacket> {

    private static final String[] DICTIONARY = {"用心计较般般错，退步思量事事难。", "点石化为金，人心犹未足。",
            "不实心，不成事；不虚心，不知事。", "与其悲叹自己的命运，不如相信自己的力量。", "眼望高山，脚踏实地。"};

    private String nextQuote() {
        int quoteId = ThreadLocalRandom.current().nextInt(DICTIONARY.length);
        return DICTIONARY[quoteId];
    }

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, DatagramPacket datagramPacket) throws Exception {
        String req = datagramPacket.content().toString(CharsetUtil.UTF_8);
        System.out.println(req);
        if ("谚语字典查询?".equals(req)) {
            channelHandlerContext.writeAndFlush(new DatagramPacket(Unpooled.copiedBuffer(
                    "谚语查询结果：　" + nextQuote(), CharsetUtil.UTF_8), datagramPacket.sender()));
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

}
