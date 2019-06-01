package websocket;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.*;
import io.netty.handler.codec.http.websocketx.*;
import io.netty.util.CharsetUtil;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-6-1
 * Description:
 */
public class WebSocketServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = Logger.getLogger(WebSocketServerHandler.class.getName());
    private WebSocketServerHandshaker handshaker;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        if (o instanceof FullHttpRequest){
            FullHttpRequest request = (FullHttpRequest) o;
            if (!request.getDecoderResult().isSuccess() || !"websocket".equals(request.headers().get("Upgrade"))){
                sendHttpResponse(channelHandlerContext, request,
                        new DefaultFullHttpResponse(HttpVersion.HTTP_1_1, HttpResponseStatus.BAD_REQUEST));
            }
            WebSocketServerHandshakerFactory wsFactory = new WebSocketServerHandshakerFactory(
                    "ws://localhost:8080/websocket", null, false);
            handshaker = wsFactory.newHandshaker(request);
            if (handshaker == null){
                WebSocketServerHandshakerFactory.sendUnsupportedVersionResponse(channelHandlerContext.channel());
            }else {
                handshaker.handshake(channelHandlerContext.channel(), request);
            }
        } else if (o instanceof WebSocketFrame){
            WebSocketFrame frame = (WebSocketFrame) o;
            if (frame instanceof CloseWebSocketFrame){
                handshaker.close(channelHandlerContext.channel(), (CloseWebSocketFrame)frame.retain());
            }
            if (!(frame instanceof TextWebSocketFrame)) {
                throw new UnsupportedOperationException(String.format("%s frame types not supported",
                        frame.getClass().getName()));
            }
            String req = ((TextWebSocketFrame) frame).text();
            if (logger.isLoggable(Level.FINE)){
                logger.fine(String.format("%s received %s", channelHandlerContext.channel(), req));
            }
            channelHandlerContext.channel().writeAndFlush(new TextWebSocketFrame(
                    req + " , 欢迎使用Netty WebSocket服务, 现在时间: " + new Date().toString()));
        }
    }

    private static void sendHttpResponse(ChannelHandlerContext ctx, FullHttpRequest req, FullHttpResponse res){
        if (res.getStatus().code() != 200){
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200){
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
