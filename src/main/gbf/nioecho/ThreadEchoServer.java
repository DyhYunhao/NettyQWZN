package nioecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.channels.SocketChannel;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description: 多线程的echo　Socket服务器，为每个客户端新建一个链接
 */
public class ThreadEchoServer {

    private static ExecutorService tp = Executors.newCachedThreadPool();

    static class HandleMsg implements Runnable{

        Socket client;

        public HandleMsg(Socket client) {
            this.client = client;
        }

        @Override
        public void run() {
            BufferedReader is = null;
            PrintWriter os = null;
            try {
                is = new BufferedReader(new InputStreamReader(client.getInputStream()));
                os = new PrintWriter(client.getOutputStream(), true);
                String input = null;
                long b = System.currentTimeMillis();
                while ((input = is.readLine()) != null){
                    os.println(input);
                }
                long e = System.currentTimeMillis();
                System.out.println("speed； " + (e - b) + "ms");
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null){
                        os.close();
                    }
                    client.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public static void main(String[] args) {
        ServerSocket echoServer = null;
        Socket client = null;

        try {
            echoServer = new ServerSocket(8000);
        } catch (IOException e) {
            e.printStackTrace();
        }

        while (true){
            try {
                if (echoServer != null) {
                    client = echoServer.accept();
                }
                if (client != null) {
                    System.out.println(client.getRemoteSocketAddress() + " connect!");
                }
                tp.execute(new HandleMsg(client));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
