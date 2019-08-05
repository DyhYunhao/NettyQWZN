package aipa;

import cn.yueshutong.util.AiPaUtil;
import cn.yueshutong.worker.AiPaWorker;
import org.jsoup.nodes.Document;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 2019/8/5
 * Description:
 */
public class MyAiPaWorker implements AiPaWorker {
    @Override
    public String run(Document document, AiPaUtil aiPaUtil) {
        //使用JSOUP进行HTML解析获取想要的div节点和属性
        //保存在数据库或本地文件中
        //新增aiPaUtil工具类可以再次请求网址
        return document.title() + document.body().text();

    }

    @Override
    public Boolean fail(String s) {
        return false;
    }
}
