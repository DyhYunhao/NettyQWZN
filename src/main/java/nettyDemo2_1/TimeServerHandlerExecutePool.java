package nettyDemo2_1;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-5-21
 * Description:
 */
public class TimeServerHandlerExecutePool {
    private ExecutorService executorService;

    public TimeServerHandlerExecutePool(int maxSize, int queueSize) {
        executorService = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                maxSize, 120L, TimeUnit.SECONDS, new ArrayBlockingQueue<Runnable>(queueSize));
    }

    public void execute(Runnable task){
        executorService.execute(task);
    }
}
