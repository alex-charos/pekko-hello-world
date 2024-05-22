package gr.charos.demo.iot;

import gr.charos.demo.iot.command.Command;
import gr.charos.demo.iot.command.ReadTemperature;
import org.apache.pekko.actor.typed.ActorRef;
import org.apache.pekko.actor.typed.ActorSystem;

public class IotMain {

    public static void main(String[] args) {
        // Create ActorSystem and top level supervisor
        ActorSystem.create(IotSupervisor.create(), "iot-system");

        ActorRef<Command>  device =  ActorSystem.create(Device.create("a_group","a_device"), "my_device");

        //device.tell(new ReadTemperature( 1L, ));


    }
}