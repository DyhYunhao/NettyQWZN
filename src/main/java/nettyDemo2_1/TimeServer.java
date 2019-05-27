package nettyDemo2_1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-13
 * Description: 同步阻塞I/O的TimeServer
 */
public class TimeServer {

    public static void main(String[] args) throws IOException {

        int port = 8080;
        if (args != null && args.length > 0){
            try {
                port = Integer.parseInt(args[0]);
            }catch (NumberFormatException e){
                e.printStackTrace();
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("this port is: " + port);
            Socket socket = null;
            //--------------伪异步I/O改造-------------------------//
            TimeServerHandlerExecutePool singleExecute = new TimeServerHandlerExecutePool(50, 1000);
            //------------------------------------------------//
            while (true) {
                socket = server.accept();
                //new Thread(new TimeServerHandler(socket)).start();
                singleExecute.execute(new TimeServerHandler(socket));
            }
        }finally {
            if (server != null){
                System.out.println("the TimeServer close");
                server.close();
                server = null;
            }
        }
    }
}

/**
 * 处理socket链路
 */
class TimeServerHandler implements Runnable{

    private Socket socket;

    TimeServerHandler(Socket socket) {
        this.socket = socket;
    }

    public void run() {

        BufferedReader in = null;
        PrintWriter out = null;
        try{
            in = new BufferedReader(new InputStreamReader(this.socket.getInputStream()));
            out = new PrintWriter(this.socket.getOutputStream(), true);
            String body = null;
            String currentTime = null;
            while (true){
                body = in.readLine();
                if (body == null)
                    break;
                System.out.println("the TimeServer receiver order: " + body);
                currentTime  = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(
                        System.currentTimeMillis()).toString() : "BAD ORDER";
                out.println(currentTime);
            }
        } catch (IOException e) {
            if (in != null){
                try {
                    in.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            if (out != null){
                out.close();
                out = null;
            }
            if (this.socket != null){
                try {
                    this.socket.close();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
                this.socket = null;
            }
        }
    }
}