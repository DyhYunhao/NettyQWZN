package akka;

import akka.actor.ActorRef;
import akka.actor.Props;
import akka.actor.UntypedActor;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class Hello extends UntypedActor {
    ActorRef greeter;
    @Override
    public void onReceive(Object message) throws Exception {
        if (message == Greeter.Msg.DONE){
            greeter.tell(Greeter.Msg.GREET, getSelf());
            getContext().stop(getSelf());
        }else {
            unhandled(message);
        }
    }

    @Override
    public void preStart() throws Exception {
        greeter = getContext().actorOf(Props.create(Greeter.class), "greeter");
        System.out.println("Greeter Actor Path: " + greeter.path());
        greeter.tell(Greeter.Msg.GREET, getSelf());
    }
}
