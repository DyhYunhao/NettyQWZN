package java_serializable;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-29
 * Description:
 */
public class TestUserInfo {

    public static void main(String[] args) throws IOException {
        UserInfo info = new UserInfo("Welcome to netty", 100);
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream os = new ObjectOutputStream(bos);
        os.writeObject(info);
        os.flush();
        os.close();
        byte[] b = bos.toByteArray();
        System.out.println("The jdk serializable length is: " + b.length);
        bos.close();
        System.out.println("---------------------------------------------");
        System.out.println("the byteArray serializable length is: " + info.codeC().length);

    }
}
