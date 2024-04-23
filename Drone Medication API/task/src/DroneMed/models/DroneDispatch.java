package DroneMed.models;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;
import java.time.LocalTime;
import java.util.List;

@Entity
public class DroneDispatch {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long dispatchId;

    @NotNull
    private boolean cancelled;

    @NotNull
    private LocalTime estimatedTime;
    //LocalTime.of(2, 30))

    private Timestamp timestamp;

    @OneToOne
    @JoinColumn(name = "serialNumber")
    private Drone drone;

    @OneToOne
    @JoinColumn(name = "phoneNumber")
    private UserAccount user;

    @OneToMany(mappedBy = "droneDispatch")
    private List<Medication> medications;

    public DroneDispatch() {
    }

    public DroneDispatch(
            Long dispatchId,
            boolean cancelled,
            LocalTime estimatedTime,
            Timestamp timestamp,
            Drone drone,
            UserAccount user,
            List<Medication> medications
    ) {
        this.dispatchId = dispatchId;
        this.cancelled = cancelled;
        this.estimatedTime = estimatedTime;
        this.timestamp = timestamp;
        this.drone = drone;
        this.user = user;
        this.medications = medications;
    }

    public Long getDispatchId() {
        return dispatchId;
    }

    public void setDispatchId(Long dispatchId) {
        this.dispatchId = dispatchId;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }

    public LocalTime getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(LocalTime estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public Drone getDrone() {
        return drone;
    }

    public void setDrone(Drone drone) {
        this.drone = drone;
    }

    public UserAccount getUser() {
        return user;
    }

    public void setUser(UserAccount user) {
        this.user = user;
    }

    public List<Medication> getMedications() {
        return medications;
    }

    public void setMedications(List<Medication> medications) {
        this.medications = medications;
    }

}
