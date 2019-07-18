/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description:线程wait和notify方法的简单使用
 */
public class SimpleWN {

    final static Object obj = new Object();
    public static class T1 extends Thread{
        public void run(){
            synchronized (obj){
                System.out.println(System.currentTimeMillis() + ": T1 start!");
                try {
                    System.out.println(System.currentTimeMillis() + ": T1 wait for object!");
                    obj.wait();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(System.currentTimeMillis() + ": T1 end!");
            }
        }
    }

    public static class T2 extends Thread{
        public void run(){
            synchronized (obj){
                System.out.println(System.currentTimeMillis() + ": T2 start! notify T1");
                obj.notify();
                System.out.println(System.currentTimeMillis() + ": T2 end!");
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static void main(String[] args) {
        Thread t1 = new T1();
        Thread t2 = new T2();
        t1.start();
        t2.start();
    }

}
