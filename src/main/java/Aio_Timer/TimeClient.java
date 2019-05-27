package Aio_Timer;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-27
 * Description:
 */
public class TimeClient {

    public static void main(String[] args){
        int port = 8080;
        if (args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }

        new Thread(new AsyncTimeClientHandle("127.0.0.1", port), "AIO-AsyncTimeClientHandle-001").start();
    }
}
