/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description: 简单的线程join(等待线程结束)实例
 */
public class JoinMain {
    public volatile static int i = 0;
    public static class AddThread extends Thread{
        @Override
        public void run() {
            for (i = 0; i < 1000000; i ++);
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread a = new AddThread();
        a.start();
        a.join(); //等待线程结束在输出，否则输出为０
        System.out.println(i);
    }
}
