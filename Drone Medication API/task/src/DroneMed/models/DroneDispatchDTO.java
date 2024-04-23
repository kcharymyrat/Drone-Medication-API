package DroneMed.models;

import lombok.Data;

import java.time.LocalTime;
import java.util.List;

public class DroneDispatchDTO {
    private Long dispatchId;
    private Boolean cancelled;
    private LocalTime estimatedTime;
    private String droneID;
    private String droneStatus;
    private String userName;
    private String userPhoneNumber;
    private List<String> medicationCodes;

    public DroneDispatchDTO(
            Long dispatchId,
            Boolean cancelled,
            LocalTime estimatedTime,
            String droneID,
            String droneStatus,
            String userName,
            String userPhoneNumber,
            List<String> medicationCodes
    ) {
        this.dispatchId = dispatchId;
        this.cancelled = cancelled;
        this.estimatedTime = estimatedTime;
        this.droneID = droneID;
        this.droneStatus = droneStatus;
        this.userName = userName;
        this.userPhoneNumber = userPhoneNumber;
        this.medicationCodes = medicationCodes;
    }

    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    public Boolean getCancelled() {
        return cancelled;
    }

    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public String getDroneID() {
        return droneID;
    }

    public void setDroneID(String droneID) {
        this.droneID = droneID;
    }

    public String getDroneStatus() {
        return droneStatus;
    }

    public void setDroneStatus(String droneStatus) {
        this.droneStatus = droneStatus;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPhoneNumber() {
        return userPhoneNumber;
    }

    public void setUserPhoneNumber(String userPhoneNumber) {
        this.userPhoneNumber = userPhoneNumber;
    }

    public List<String> getMedicationCodes() {
        return medicationCodes;
    }

    public void setMedicationCodes(List<String> medicationCodes) {
        this.medicationCodes = medicationCodes;
    }
}
