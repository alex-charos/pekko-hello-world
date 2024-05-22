package gr.charos.demo.iot;

import gr.charos.demo.iot.command.*;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.PostStop;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

import java.util.Optional;

public class Device  extends AbstractBehavior<Command> {

    private final String groupId;
    private final String deviceId;
    private Optional<Double> lastTemperatureReading = Optional.empty();

    public static Behavior<Command> create(String groupId, String deviceId) {
        return Behaviors.setup(context -> new Device(context, groupId, deviceId));
    }

    private Device(ActorContext<Command> context, String groupId, String deviceId) {
        super(context);
        this.groupId = groupId;
        this.deviceId = deviceId;

        context.getLog().info("Device actor {}-{} started", groupId, deviceId);
    }

    @Override
    public Receive<Command> createReceive() {
        return newReceiveBuilder()
                .onMessage(ReadTemperature.class, this::onReadTemperature)
                .onMessage(RecordTemperature.class, this::onRecordTemperature)
                .onSignal(PostStop.class, signal -> onPostStop())
                .build();

    }

    private Behavior<Command> onRecordTemperature(RecordTemperature r) {
        getContext().getLog().info("Recorded temperature reading {} with {}", r.getValue(), r.getRequestId());
        lastTemperatureReading = Optional.of(r.getValue());
        r.getReplyTo().tell(new TemperatureRecorded(r.getRequestId()));
        return this;
    }

    private Behavior<Command> onReadTemperature(ReadTemperature r) {
        r.getReplyTo().tell(new RespondTemperature(r.getRequestId(), lastTemperatureReading));
        return this;
    }

    private Device onPostStop() {
        getContext().getLog().info("Device actor {}-{} stopped", groupId, deviceId);
        return this;
    }


}