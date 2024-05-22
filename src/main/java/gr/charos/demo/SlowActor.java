package gr.charos.demo;

import org.apache.pekko.actor.AbstractActor;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class SlowActor  extends AbstractBehavior<String> {
    static Behavior<String> create() {
        return Behaviors.setup(SlowActor::new);
    }
    public SlowActor(ActorContext<String> context) {
        super(context);
    }


    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("doSomething", this::doSomething)
                .build();
    }

    private Behavior<String> doSomething() throws Exception {
        System.out.println("doSomething start");
        Thread.sleep(4000);
        System.out.println("doSomething end");
        return this;
    }


}
