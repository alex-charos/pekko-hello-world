package gr.charos.demo.iot.command;

import gr.charos.demo.iot.reply.Reply;
import gr.charos.demo.iot.reply.RespondTemperature;
import org.apache.pekko.actor.typed.ActorRef;

public final class ReadTemperature implements Command {
    final long requestId;
    final ActorRef<Reply> replyTo;

    public ReadTemperature(long requestId, ActorRef<Reply> replyTo) {
        this.requestId = requestId;
        this.replyTo = replyTo;
    }

    public long getRequestId() {
        return requestId;
    }

    public ActorRef<Reply> getReplyTo() {
        return replyTo;
    }
}
