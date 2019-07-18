/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-17
 * Description:32位机器中对long类型的读取不是原子性的，导致输出错误信息．
 */
public class MultiThreadLong {

    public static long t = 0;
    public static class ChangeT implements Runnable{

        private long to;

        public ChangeT(long to) {
            this.to = to;
        }

        @Override
        public void run() {

            while (true){
                MultiThreadLong.t = to;
                Thread.yield();
            }
        }
    }

    public static class ReadT implements Runnable{

        @Override
        public void run() {

            while (true){
                long temp = MultiThreadLong.t;
                System.err.println(temp);
                if (temp != 111L && temp != -999L && temp != 333L && temp != -444L){
                    System.out.println(temp);
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) {
        new Thread(new ChangeT(111L)).start();
        new Thread(new ChangeT(-999L)).start();
        new Thread(new ChangeT(333L)).start();
        new Thread(new ChangeT(-444L)).start();
        new Thread(new ReadT()).start();
    }

}
