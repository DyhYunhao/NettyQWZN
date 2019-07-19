package nioecho;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.Socket;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class EchoClient {

    public static void main(String[] args) throws IOException {
        Socket socket = null;
        PrintWriter writer = null;
        BufferedReader reader = null;

        try {
            socket = new Socket();
            socket.connect(new InetSocketAddress("localhost", 8000));
            writer = new PrintWriter(socket.getOutputStream(), true);
            writer.println("Hello!");
            writer.flush();
            reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("from server: " + reader.readLine());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if (writer != null){
                writer.close();
            }
            if (reader != null){
                reader.close();
            }
            if (socket != null){
                socket.close();
            }
        }
    }

}
