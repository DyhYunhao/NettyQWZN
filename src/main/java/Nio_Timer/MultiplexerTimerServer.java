package Nio_Timer;

import java.io.BufferedReader;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-21
 * Description: 多路复用类,轮询多路复用器Selector,可以处理多个客户端的并发接入
 */
public class MultiplexerTimerServer implements Runnable {

    private Selector selector;

    private ServerSocketChannel serverSocketChannel;

    private volatile boolean stop;

    public MultiplexerTimerServer(int port) {
        try {
            selector = Selector.open();
            serverSocketChannel = ServerSocketChannel.open();
            serverSocketChannel.configureBlocking(false);
            serverSocketChannel.socket().bind(new InetSocketAddress(port), 1024);
            serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("the time server is start in port: " + port);
        }catch (IOException e) {
            e.printStackTrace();
            System.exit(1);
        }
    }

    public void stop(){
        this.stop = true;
    }

    public void run() {
        while (!stop){
            try {
                selector.select(1000);
                Set<SelectionKey> selectionKeys = selector.selectedKeys();
                Iterator<SelectionKey> iterator = selectionKeys.iterator();
                SelectionKey key = null;
                while (iterator.hasNext()){
                    key = iterator.next();
                    iterator.remove();
                    try {
                        handlerInput(key);
                    } catch (Exception e) {
                        if (key != null){
                            key.cancel();
                            if (key.channel() != null){
                                key.channel().close();
                            }
                        }
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (selector != null){
            try {
                selector.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void handlerInput(SelectionKey key) throws IOException {

        if (key.isValid()){
            if (key.isAcceptable()){
                ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
                SocketChannel soc = ssc.accept();
                soc.configureBlocking(false);
                soc.register(selector, SelectionKey.OP_READ);
            }
            if (key.isReadable()){
                SocketChannel soc = (SocketChannel) key.channel();
                ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                int readBytes = soc.read(byteBuffer);
                if (readBytes  > 0){
                    byteBuffer.flip();
                    byte[] bytes = new byte[byteBuffer.remaining()];
                    byteBuffer.get(bytes);
                    String body = new String(bytes, "UTF-8");
                    System.out.println("the time server receive order: " + body);

                    String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ?
                            new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
                    dowrite(soc, currentTime);
                }else if (readBytes < 0){
                    key.cancel();
                    soc.close();
                }else;
            }
        }
    }

    private void dowrite(SocketChannel sChannel, String resp) throws IOException {
        if (resp != null && resp.trim().length() > 0){
            byte[] bytes = resp.getBytes();
            ByteBuffer wbuffer = ByteBuffer.allocate(bytes.length);
            wbuffer.put(bytes);
            wbuffer.flip();
            sChannel.write(wbuffer);
        }
    }
}
