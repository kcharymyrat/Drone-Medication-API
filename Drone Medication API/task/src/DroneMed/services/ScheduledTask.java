package DroneMed.services;


import DroneMed.models.Drone;
import DroneMed.models.DroneDispatch;
import DroneMed.models.DroneState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Component
public class ScheduledTask {
    private final DroneService droneService;

    @Autowired
    public ScheduledTask(DroneService droneService) {
        this.droneService = droneService;
    }


    public void droneDispatchedStateTask(DroneDispatch droneDispatch) {
        System.out.println("droneDispatchedStateTask called");

        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(3);

        LocalTime localTime = droneDispatch.getEstimatedTime();
        long deliverTime = convertLocalTimeToMilliseconds(localTime);
        int batteryConsumptionPerSec = 10;
        int batConsumption = (int)((deliverTime / 1000) * batteryConsumptionPerSec);
        Drone drone = droneDispatch.getDrone();

        updateDrone(Mode.DELIVERING, batConsumption, drone);

        scheduler.schedule(() -> {
            updateDrone(Mode.DELIVERING,batConsumption, drone);
            System.out.println("Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " with a battery level of " + drone.getBatteryCapacity() + " amps is on a medication delivery.");

            String logInfo1 = "Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " with a battery level of " + drone.getBatteryCapacity() + " amps is on medication delivery.";
            System.out.println("logInfo1 = " + logInfo1);

            try {
                writeToFile(logInfo1);
            }
            catch (Exception e){
                System.out.println("Exception writing to file " + e.getMessage().toString());
            }

            scheduler.schedule(() -> {
                updateDrone(Mode.DELIVERED,batConsumption, drone);
                updateDrone(Mode.RETURNING,batConsumption, drone);

                String logInfo2 = "Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " delivery is complete! battery level " + drone.getBatteryCapacity() + "amps, drone returning to base.";
                System.out.println("logInfo2 = " + logInfo2);

                try {
                    writeToFile(logInfo2);
                }
                catch (Exception e){
                    System.out.println("Exception writing to file " + e.getMessage().toString());
                }

                scheduler.schedule(() -> {
                    updateDrone(Mode.IDLE,batConsumption, drone);

                    String logInfo3 = "Timestamp " + Timestamp.from(Instant.now()) + " Drone " + drone.getSerialNumber() + " has arrived at the base, battery level " + drone.getBatteryCapacity();

                    try {
                        writeToFile(logInfo3);
                    }
                    catch (Exception e){
                        System.out.println("Exception writing to file " + e.getMessage().toString());
                    }

                    scheduler.shutdown();

                }, deliverTime, TimeUnit.MILLISECONDS);

            }, deliverTime, TimeUnit.MILLISECONDS);

        }, 0, TimeUnit.MILLISECONDS);
    }

    private void updateDrone(Mode mode, int tripConsumption, Drone drone) {
        if(mode == Mode.DELIVERING) {
            drone.setState(DroneState.DELIVERING);
            droneService.updateDrone(drone);
        }
        else if (mode == Mode.DELIVERED ) {
            drone.setState(DroneState.DELIVERED);
            int currentBattery = drone.getBatteryCapacity();
            drone.setBatteryCapacity(currentBattery - tripConsumption);
            droneService.updateDrone(drone);
        }
        else if (mode == Mode.RETURNING ) {
            drone.setState(DroneState.RETURNING);
            droneService.updateDrone(drone);
        }
        else if (mode == Mode.IDLE ) {
            drone.setState(DroneState.IDLE);
            int currentBattery = drone.getBatteryCapacity();
            drone.setBatteryCapacity(currentBattery - tripConsumption);
            droneService.updateDrone(drone);
        }
    }


    private void writeToFile(String data) throws IOException {
        String currentPath = System.getProperty("user.dir");
        System.out.println("Current Path: " + currentPath);
        System.out.println("data = " + data);

        String filePath = "log/dispatchlog.txt";
        File file = new File(filePath);
        System.out.println("file = " + file);
        try (FileWriter writer = new FileWriter(file, true)) {
            writer.write(data + System.lineSeparator());
        }
    }

    private static long convertLocalTimeToMilliseconds(LocalTime localTime) {
        int hours = localTime.getHour();
        int minutes = localTime.getMinute();
        int seconds = localTime.getSecond();
        return (hours * 60 * 60 + minutes * 60 + seconds) * 1000;
    }


    public enum Mode {
        IDLE,
        DELIVERING,
        DELIVERED,
        RETURNING
    }

}
