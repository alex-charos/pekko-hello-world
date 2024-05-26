package gr.charos.demo.iot.reply;

public class TemperatureRecorded implements Reply {

    final long requestId;
    public TemperatureRecorded(long requestId) {
        this.requestId = requestId;
    }

    public long getRequestId() {
        return requestId;
    }
}
