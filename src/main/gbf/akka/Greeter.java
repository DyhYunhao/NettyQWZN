package akka;

import akka.actor.UntypedActor;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class Greeter extends UntypedActor {

    public static enum Msg{
        GREET, DONE;
    }

    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Msg.GREET){
            System.out.println("Hello World!");
            getSender().tell(Msg.DONE, getSelf());
        } else {
            unhandled(message);
        }
    }
}
