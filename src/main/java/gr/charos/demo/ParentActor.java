package gr.charos.demo;

import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.PostStop;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class ParentActor extends AbstractBehavior<String> {
    static Behavior<String> create() {
        return Behaviors.setup(ParentActor::new);
    }

    private final ActorRef<String> slow;

    public ParentActor(ActorContext<String> context) {
        super(context);
        System.out.println("ParentActor created");
        context.spawn(ChildActor.create(), "second-actor");
        slow = context.spawn(SlowActor.create(), "slow-actor");
    }

    @Override
    public Receive<String> createReceive() {
        return newReceiveBuilder()
                .onMessageEquals("stop", Behaviors::stopped)
                .onSignal(PostStop.class, signal -> onPostStop())
                .onMessageEquals("start", this::start)
                .onMessageEquals("doSomethingSlow", this::onSlow)
                .build();
    }
    private Behavior<String> onPostStop() {
        System.out.println("first stopped");
        return this;
    }
    private Behavior<String> onSlow() {
        System.out.println("first slow Start");
        slow.tell("doSomething");
        System.out.println("first slow End");

        return  this;

    }

    private Behavior<String> start() {
        ActorRef<String> firstRef = getContext().spawn(ChildActor.create(), "first-actor");

        System.out.println("First: " + firstRef+ " "+ Thread.currentThread().getName());
        firstRef.tell("printit");
        return Behaviors.same();
    }
}
