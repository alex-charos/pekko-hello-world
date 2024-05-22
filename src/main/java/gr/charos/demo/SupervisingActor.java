package gr.charos.demo;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.SupervisorStrategy;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class SupervisingActor extends AbstractBehavior<String> {
    static Behavior<String> create() {
        return Behaviors.setup(SupervisingActor::new);
    }

    private final ActorRef<String> child;

    private SupervisingActor(ActorContext<String> context) {
        super(context);
//        child =
//                context.spawn(
//                        Behaviors.supervise(SupervisedActor.create()).onFailure(SupervisorStrategy.restart()),
//                        "supervised-actor");
       child = context.spawn(SupervisedActor.create(), "supervised-actor");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder().onMessageEquals("failChild", this::onFailChild).build();
    }

    private Behavior<String> onFailChild() {
        child.tell("fail");
        return this;
    }
}
