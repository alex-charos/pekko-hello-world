package gr.charos.demo.iot.command;

import gr.charos.demo.iot.Device;
import org.apache.pekko.actor.typed.ActorRef;

public final class ReadTemperature implements Command {
    final long requestId;
    final ActorRef<RespondTemperature> replyTo;

    public ReadTemperature(long requestId, ActorRef<RespondTemperature> replyTo) {
        this.requestId = requestId;
        this.replyTo = replyTo;
    }

    public long getRequestId() {
        return requestId;
    }

    public ActorRef<RespondTemperature> getReplyTo() {
        return replyTo;
    }
}
