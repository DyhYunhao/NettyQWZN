package bxlsx;

import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class Div implements Runnable {
    public static BlockingDeque<Msg> bq = new LinkedBlockingDeque<>();

    @Override
    public void run() {
        while (true) {
            try {
                Msg msg = bq.take();
                msg.i = msg.i / 2;
                System.out.println(msg.orgStr + "= " + msg.i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
