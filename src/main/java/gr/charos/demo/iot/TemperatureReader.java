package gr.charos.demo.iot;

import gr.charos.demo.iot.command.Command;
import gr.charos.demo.iot.command.ReadTemperature;
import gr.charos.demo.iot.command.RecordTemperature;
import gr.charos.demo.iot.reply.Reply;
import gr.charos.demo.iot.reply.RespondTemperature;
import gr.charos.demo.iot.reply.TemperatureRecorded;
import org.apache.pekko.actor.typed.Behavior;
import org.apache.pekko.actor.typed.PostStop;
import org.apache.pekko.actor.typed.javadsl.AbstractBehavior;
import org.apache.pekko.actor.typed.javadsl.ActorContext;
import org.apache.pekko.actor.typed.javadsl.Behaviors;
import org.apache.pekko.actor.typed.javadsl.Receive;

public class TemperatureReader extends AbstractBehavior<Reply> {
    private final String groupId;
    private final String deviceId;

    public static Behavior<Reply> create(String groupId, String deviceId) {
        return Behaviors.setup(context -> new TemperatureReader(context, groupId, deviceId));
    }


    private TemperatureReader(ActorContext<Reply> context, String groupId, String deviceId) {
        super(context);
        this.groupId = groupId;
        this.deviceId = deviceId;
    }

    @Override
    public Receive<Reply> createReceive() {
        return newReceiveBuilder()
                .onMessage(RespondTemperature.class, this::onReadTemperature)
                .onMessage(TemperatureRecorded.class, this::onRecordTemperature)
                .build();
    }

    private Behavior<Reply> onReadTemperature(RespondTemperature reply) {

        getContext().getLog().info("Temperature received: {}, {}" , reply.getRequestId() , reply.getValue());
        return this ;
    }

    private Behavior<Reply> onRecordTemperature(TemperatureRecorded reply) {
        getContext().getLog().info("Temperature recorded successfully for {}" , reply.getRequestId());
        return this ;
    }
}
