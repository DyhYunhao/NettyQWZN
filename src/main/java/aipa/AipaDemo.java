package aipa;

import cn.yueshutong.AiPa;
import cn.yueshutong.executor.AiPaExecutor;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 2019/8/5
 * Description:
 */
public class AipaDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        List<String> linkList = new ArrayList<>();
        linkList.add("http://jb39.com/jibing/FeiQiZhong265988.htm");
        linkList.add("http://jb39.com/jibing/XiaoErGuoDu262953.htm");
        linkList.add("http://jb39.com/jibing/GaoYuanFeiShuiZhong260310.htm");
        linkList.add("http://jb39.com/zhengzhuang/LuoYin337449.htm");
        AiPaExecutor aiPaExecutor = AiPa.newInstance(new MyAiPaWorker()).setCharset(Charset.forName("GBK"));
        for (int i = 0; i < 8; i ++){
            aiPaExecutor.submit(linkList);
        }
        List<Future> futureList = aiPaExecutor.getFutureList();
        for (int i = 0; i < futureList.size(); i ++){
            System.out.println(futureList.get(i).get());
        }
        aiPaExecutor.shutdown();
    }
}
