package gr.charos.demo;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;

public class Main {

    public static void main(String[] args) {
       // ActorRef<String> testSystem = ActorSystem.create(ParentActor.create(), "testSystem");
       // testSystem.tell("stop");

        ActorRef<String> testSystem = ActorSystem.create(ParentActor.create(), "testSystem");
        testSystem.tell("failChild");
        testSystem.tell("failChild");
        testSystem.tell("doSomethingSlow");
    }
}
