package nioecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.LockSupport;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class HeavyClient {

    private static ExecutorService tp = Executors.newCachedThreadPool();
    private static final int sleep = 1000 * 1000 * 1000;

    public static class EchoClient implements Runnable{

        @Override
        public void run() {
            Socket client = null;
            PrintWriter writer = null;
            BufferedReader reader = null;

            try {
                client = new Socket();
                client.connect(new InetSocketAddress("localhost", 8000));
                writer = new PrintWriter(client.getOutputStream(), true);
                writer.println("H");
                LockSupport.parkNanos(sleep);
                writer.println("e");
                LockSupport.parkNanos(sleep);
                writer.println("l");
                LockSupport.parkNanos(sleep);
                writer.println("l");
                LockSupport.parkNanos(sleep);
                writer.println("o");
                LockSupport.parkNanos(sleep);
                writer.println("!");
                LockSupport.parkNanos(sleep);

                writer.println();
                writer.flush();

                reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println("from server: " + reader.readLine());
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (writer != null){
                    writer.close();
                }
                if (reader != null){
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (client != null){
                    try {
                        client.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        EchoClient echoClient = new EchoClient();
        for (int i = 0; i < 10; i ++){
            tp.execute(echoClient);
        }
    }

}
