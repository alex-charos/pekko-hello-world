package gr.charos.demo.iot.command;

public class TemperatureRecorded {
    final long requestId;

    public TemperatureRecorded(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() {
        return requestId;
    }
}
