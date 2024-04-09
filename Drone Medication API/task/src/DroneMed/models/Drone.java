package DroneMed.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Drone {
    @Id
    private String serialNumber;
    private int maxWeight;
    private int currentWeight;
    private int batteryCapacity;

    @Enumerated(EnumType.STRING)
    private DroneState State;

    @Enumerated(EnumType.STRING)
    private DroneModel model;
    private String currentCoordinate;

    public Drone() {
    }

    public Drone(
            String serialNumber,
            int maxWeight,
            int currentWeight,
            int batteryCapacity,
            DroneState state,
            DroneModel model,
            String currentCoordinate
    ) {
        this.serialNumber = serialNumber;
        this.maxWeight = maxWeight;
        this.currentWeight = currentWeight;
        this.batteryCapacity = batteryCapacity;
        this.State = state;
        this.model = model;
        this.currentCoordinate = currentCoordinate;
    }

    public String getSerialNumber() {
        return serialNumber;
    }

    public void setSerialNumber(String serialNumber) {
        this.serialNumber = serialNumber;
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public void setMaxWeight(int maxWeight) {
        this.maxWeight = maxWeight;
    }

    public int getCurrentWeight() {
        return currentWeight;
    }

    public void setCurrentWeight(int currentWeight) {
        this.currentWeight = currentWeight;
    }

    public int getBatteryCapacity() {
        return batteryCapacity;
    }

    public void setBatteryCapacity(int batteryCapacity) {
        this.batteryCapacity = batteryCapacity;
    }

    public DroneState getState() {
        return State;
    }

    public void setState(DroneState state) {
        this.State = state;
    }

    public DroneModel getModel() {
        return model;
    }

    public void setModel(DroneModel model) {
        this.model = model;
    }

    public String getCurrentCoordinate() {
        return currentCoordinate;
    }

    public void setCurrentCoordinate(String currentCoordinate) {
        this.currentCoordinate = currentCoordinate;
    }

    @Override
    public String toString() {
        return "Drone serialnumber: " + this.getSerialNumber() + " maxWeight: " + this.getMaxWeight() + " currentWeight: " + this.getCurrentWeight() + " batteryCapacity: " + this.getBatteryCapacity() +
                " State: " + this.getState() + " Model: " + this.model + " Coordinate: " + this.getCurrentCoordinate();
    }
}
