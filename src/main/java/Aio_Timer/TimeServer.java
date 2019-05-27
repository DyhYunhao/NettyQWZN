package Aio_Timer;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-24
 * Description:
 */
public class TimeServer {

    public static void main(String[] args){
        int port = 8080;
        if (args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException ignored){

            }
        }

        AsyncTimeServerHandle timeServerHandle = new AsyncTimeServerHandle(port);
        new Thread(timeServerHandle, "AIO-AsyncTimeServerHandle-001").start();
    }

}
