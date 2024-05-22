package gr.charos.demo.iot.command;

import org.apache.pekko.actor.typed.ActorRef;

public class RecordTemperature implements Command {

    final long requestId;
    final double value;
    final ActorRef<TemperatureRecorded> replyTo;

    public RecordTemperature(long requestId, double value, ActorRef<TemperatureRecorded> replyTo) {
        this.requestId = requestId;
        this.value = value;
        this.replyTo = replyTo;
    }

    public double getValue() {
        return value;
    }

    public long getRequestId() {
        return requestId;
    }

    public ActorRef<TemperatureRecorded> getReplyTo() {
        return replyTo;
    }
}
