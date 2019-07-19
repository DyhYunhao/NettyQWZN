package bxlsx;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class Mutiply implements Runnable {
    public static BlockingDeque<Msg> bq = new LinkedBlockingDeque<>();

    @Override
    public void run() {
        while (true){
            try {
                Msg msg = bq.take();
                msg.i = msg.i * msg.j;
                Div.bq.add(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
