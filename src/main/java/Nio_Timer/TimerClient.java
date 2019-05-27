package Nio_Timer;

import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-22
 * Description:
 */
public class TimerClient {
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0){
            port = Integer.parseInt(args[0]);
        }

        new Thread(new TimeClientHandle("127.0.0.1", port), "TimeClient-001").start();

    }
}
