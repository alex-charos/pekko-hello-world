package gr.charos.demo.iot.reply;

import java.util.Optional;

public final class RespondTemperature implements Reply {
    final long requestId;
    final Optional<Double> value;

    public RespondTemperature(long requestId, Optional<Double> value) {
        this.requestId = requestId;
        this.value = value;
    }

    public Optional<Double> getValue() {
        return value;
    }

    public long getRequestId() {
        return requestId;
    }
}