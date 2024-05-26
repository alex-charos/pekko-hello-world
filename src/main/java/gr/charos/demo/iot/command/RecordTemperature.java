package gr.charos.demo.iot.command;

import gr.charos.demo.iot.reply.Reply;
import gr.charos.demo.iot.reply.TemperatureRecorded;
import org.apache.pekko.actor.typed.ActorRef;

public class RecordTemperature implements Command {

    final long requestId;
    final double value;
    final ActorRef<Reply> replyTo;

    public RecordTemperature(long requestId, double value, ActorRef<Reply> replyTo) {
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

    public ActorRef<Reply> getReplyTo() {
        return replyTo;
    }
}
