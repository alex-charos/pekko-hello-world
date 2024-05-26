package gr.charos.demo.iot;

import gr.charos.demo.iot.command.*;
import gr.charos.demo.iot.reply.Reply;
import gr.charos.demo.iot.reply.RespondTemperature;
import gr.charos.demo.iot.reply.TemperatureRecorded;
import org.apache.pekko.actor.testkit.typed.javadsl.TestKitJunitResource;
import org.apache.pekko.actor.testkit.typed.javadsl.TestProbe;
import org.apache.pekko.actor.typed.ActorRef;
import org.junit.ClassRule;
import org.junit.Test;

import java.util.Optional;

import static org.junit.Assert.assertEquals;

public class DeviceTest {
    @ClassRule
    public static final TestKitJunitResource testKit = new TestKitJunitResource();

    @Test
    public void testReplyWithEmptyReadingIfNoTemperatureIsKnown() {
        TestProbe<Reply> probe = testKit.createTestProbe(Reply.class);
        ActorRef<Command> deviceActor = testKit.spawn(Device.create("group", "device"));

        deviceActor.tell(new ReadTemperature(42L, probe.getRef()));
        RespondTemperature response = (RespondTemperature) probe.receiveMessage();
        assertEquals(42L, response.getRequestId());
        assertEquals(Optional.empty(), response.getValue());
    }

    @Test
    public void testReplyWithLatestTemperatureReading() {
        TestProbe<Reply> recordProbe =
                testKit.createTestProbe(Reply.class);
        TestProbe<Reply> readProbe =
                testKit.createTestProbe(Reply.class);
        ActorRef<Command> deviceActor = testKit.spawn(Device.create("group", "device"));

        deviceActor.tell(new RecordTemperature(1L, 24.0, recordProbe.getRef()));
        assertEquals(1L, ((TemperatureRecorded) recordProbe.receiveMessage()).getRequestId());

        deviceActor.tell(new ReadTemperature(2L, readProbe.getRef()));
        RespondTemperature response1 = (RespondTemperature) readProbe.receiveMessage();
        assertEquals(2L,  response1.getRequestId());
        assertEquals(Optional.of(24.0), response1.getValue());

        deviceActor.tell(new RecordTemperature(3L, 55.0, recordProbe.getRef()));
        assertEquals(3L, ((TemperatureRecorded) recordProbe.receiveMessage()).getRequestId());

        deviceActor.tell(new ReadTemperature(4L, readProbe.getRef()));
        RespondTemperature response2 = (RespondTemperature) readProbe.receiveMessage();
        assertEquals(4L, response2.getRequestId());
        assertEquals(Optional.of(55.0), response2.getValue());
    }
}
