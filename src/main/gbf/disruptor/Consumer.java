package disruptor;

import com.lmax.disruptor.WorkHandler;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-18
 * Description:x
 */
public class Consumer implements WorkHandler<PCData> {
    @Override
    public void onEvent(PCData pcData) throws Exception {
        System.out.println(Thread.currentThread().getId() + ": Event: --" + pcData.getValue() * pcData.getValue() + "--" );

    }
}
