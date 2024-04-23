package DroneMed.models;

public class MedicationDTO {
    private String code;

    private String name;

    private int weight;

    private String imageURL;

    private long droneDispatchID;

    public MedicationDTO(
            String code,
            String name,
            int weight,
            String imageURL,
            long droneDispatchID
    ) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.imageURL = imageURL;
        this.droneDispatchID = droneDispatchID;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getImageURL() {
        return imageURL;
    }

    public void setImageURL(String imageURL) {
        this.imageURL = imageURL;
    }

    public long getDroneDispatchID() {
        return droneDispatchID;
    }

    public void setDroneDispatchID(long droneDispatchID) {
        this.droneDispatchID = droneDispatchID;
    }
}
