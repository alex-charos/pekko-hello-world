package gr.charos.demo;

import org.apache.pekko.actor.Actor;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.PostStop;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class ChildActor extends AbstractBehavior<String> {

    static Behavior<String> create() {
        return Behaviors.setup(ChildActor::new);
    }

    public ChildActor(ActorContext<String> context) {
        super(context);
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                    .onMessageEquals("printit", this::printIt)
                    .onSignal(PostStop.class, signal -> onPostStop())
                .build();

    }

    private Behavior<String> onPostStop() {
        System.out.println("second stopped");
        return this;
    }

    private Behavior<String> printIt() {
        ActorRef<String> secondRef = getContext().spawn(Behaviors.empty(), "second-actor");
        System.out.println("Second: " + secondRef + " "+ Thread.currentThread().getName());
        return this;
    }

}
