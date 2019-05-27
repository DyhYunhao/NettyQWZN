package Aio_Timer;

import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-27
 * Description:
 */
public class AcceptCompletionHandle implements CompletionHandler<AsynchronousSocketChannel, AsyncTimeServerHandle> {

    @Override
    public void completed(AsynchronousSocketChannel result, AsyncTimeServerHandle attachment) {
        attachment.asynchronousServerSocketChannel.accept(attachment, this);
        ByteBuffer buffer = ByteBuffer.allocate(1024);
        result.read(buffer, buffer, new ReadCompletionHandle(result));
    }

    @Override
    public void failed(Throwable exc, AsyncTimeServerHandle attachment) {
        exc.printStackTrace();
        attachment.latch.countDown();
    }
}
