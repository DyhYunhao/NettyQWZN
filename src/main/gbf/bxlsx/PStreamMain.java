package bxlsx;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class PStreamMain {
    public static void main(String[] args) {
        new Thread(new Plus()).start();
        new Thread(new Mutiply()).start();
        new Thread(new Div()).start();

        for (int i = 1; i <= 10000; i ++){
            for (int j = 1; j <= 1000; j ++){
                Msg msg = new Msg();
                msg.i = i;
                msg.j = j;
                msg.orgStr = "((" + i + " + " + j + ") * " + i + " / 2";
                Plus.bq.add(msg);
            }
        }
    }
}
