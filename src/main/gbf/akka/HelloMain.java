package akka;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import com.typesafe.config.ConfigFactory;

/**
 * Created with IntelliJ IDEA.
 * User: daiyunhao
 * Date: 19-7-19
 * Description:
 */
public class HelloMain {

    public static void main(String[] args) {
        ActorSystem system = ActorSystem.create("Hello", ConfigFactory.load("hello.conf"));
        ActorRef a = system.actorOf(Props.create(Hello.class), "Hello");
        System.out.println("Hello Actor Path: " + a.path());
    }

}
