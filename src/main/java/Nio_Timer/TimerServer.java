package Nio_Timer;

import java.awt.image.MultiPixelPackedSampleModel;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-21
 * Description:
 */
public class TimerServer {

    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        MultiplexerTimerServer timerServer = new MultiplexerTimerServer(port);
        new Thread(timerServer, "NIO-MultiplexerTimerServer-001").start();
    }

}
