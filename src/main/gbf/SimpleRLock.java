import java.util.concurrent.locks.ReentrantLock;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description:简单的重入锁(ReentrantLock)实例
 */
public class SimpleRLock implements Runnable {

    public static ReentrantLock lock = new ReentrantLock();
    public static int i = 0;

    @Override
    public void run() {
        for (int j = 0; j < 1000000; j ++){
            lock.lock();
            try {
                i ++;
            }finally {
                lock.unlock();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        SimpleRLock t1 = new SimpleRLock();
        Thread ta = new Thread(t1);
        Thread tb = new Thread(t1);
        ta.start();
        tb.start();
        ta.join();
        tb.join();
        System.out.println(i);
    }
}
