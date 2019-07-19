package bxlsx;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class Plus implements Runnable {
    public static BlockingDeque<Msg> bq = new LinkedBlockingDeque<>();
    @Override
    public void run() {
        while (true){
            try {
                Msg msg = bq.take();
                msg.j = msg.i + msg.j;
                Mutiply.bq.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
