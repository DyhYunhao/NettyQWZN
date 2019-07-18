import org.omg.PortableInterceptor.INACTIVE;

import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description: 线程不安全的ArrayList　可以使用线程安全的vector
 */
public class ArrayListNosafe {

    static ArrayList<Integer> a = new ArrayList<>(10);

    public static class AddThread extends Thread{
        @Override
        public void run() {
            for (int i = 0; i < 1000000; i ++){
                a.add(i);
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(new AddThread());
        Thread t2 = new Thread(new AddThread());
        t1.start();
        t2.start();
        t1.join();
        t2.join();
        System.out.println(a.size());
    }
}
