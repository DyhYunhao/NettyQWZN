package java_serializable;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-29
 * Description:
 */
public class UserInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    private String username;
    private int userId;

    public UserInfo(String username, int userId) {
        this.username = username;
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public byte[] codeC(){
        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
        byte[] value = this.username.getBytes();
        byteBuffer.putInt(value.length);
        byteBuffer.put(value);
        byteBuffer.putInt(this.userId);
        byteBuffer.flip();
        value = null;
        byte[] result = new byte[byteBuffer.remaining()];
        byteBuffer.get(result);
        return result;
    }
}
