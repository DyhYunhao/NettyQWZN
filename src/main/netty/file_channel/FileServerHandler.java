package file_channel;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.DefaultFileRegion;
import io.netty.channel.FileRegion;
import io.netty.channel.SimpleChannelInboundHandler;
import jdk.management.resource.internal.ResourceNatives;

import java.io.File;
import java.io.RandomAccessFile;

import static io.netty.handler.codec.http.HttpConstants.CR;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-6-3
 * Description:
 */
public class FileServerHandler extends SimpleChannelInboundHandler<String> {
    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, String s) throws Exception {
        File file = new File(s);
        if (file.exists()){
            if (!file.isFile()){
                channelHandlerContext.writeAndFlush("Not a file: " + file + CR);
                return;
            }
            channelHandlerContext.write(file + "  " + file.length() + CR);
            RandomAccessFile randomAccessFile = new RandomAccessFile(s, "r");
            FileRegion region = new DefaultFileRegion(randomAccessFile.getChannel(), 0, randomAccessFile.length());
            channelHandlerContext.write(region);
            channelHandlerContext.writeAndFlush(CR);
            randomAccessFile.close();
        }else {
            channelHandlerContext.writeAndFlush("File not found: " + file + CR);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
