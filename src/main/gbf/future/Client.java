package future;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description:
 */
public class Client {
    public Data request(final String queryStr){
        final FutureData futureData = new FutureData();
        new Thread(){
            @Override
            public void run() {
                RealData realData = new RealData(queryStr);
                futureData.setRealData(realData);
            }
        }.start();
        return futureData;
    }

    public static void main(String[] args) {
        Client client = new Client();
        Data data = client.request("name");
        System.out.println("请求完毕");
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("数据= " + data.getResult());
    }
}
