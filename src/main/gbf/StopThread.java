/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description: 线程执行完后会自动终止，但有些不会．虽然提供了stop方法终止线程，容易导致数据不一致
 * 可以通过设置一个变量自行决定线程结束时间，如stopMe()方法
 */
public class StopThread {

    public static User u = new User();

    public static class User{
        private int id;
        private String name;

        public User() {
            this.id = 0;
            this.name = "0";
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        @Override
        public String toString() {
            return "User [id=" + id + ", name=" + name + "]";
        }
    }

    public static class ChangeObjectThread extends Thread{

        //通过设置volatile变量来决定线程的结束时间从而改善stop方法导致数据不一致的弊端
        volatile boolean stopme = false;

        public void stopMe(){
            stopme = true;
        }

        @Override
        public void run() {
            while (true){

                if (stopme){
                    System.out.println("exit by stopMe");
                    break;
                }

                synchronized (u){
                    int v = (int)(System.currentTimeMillis() / 1000);
                    u.setId(v);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                    u.setName(String.valueOf(v));
                }
                Thread.yield();
            }
        }
    }

    public static class ReadObjectThread extends Thread{
        @Override
        public void run() {
            while (true){
                synchronized (u){
                   if (u.getId() != Integer.parseInt(u.getName())){
                       System.out.println(u.toString());
                   }
                }
                Thread.yield();
            }
        }
    }

    public static void main(String[] args) throws InterruptedException {
        new ReadObjectThread().start();
        while (true){
            Thread t = new ChangeObjectThread();
            t.start();
            Thread.sleep(150);
            //t.stop已经将被废弃，容易导致数据不一致，如果不是特别需要，不要使用
//            t.stop();
        }
    }

}
