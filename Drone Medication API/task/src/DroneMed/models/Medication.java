package DroneMed.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class Medication {
    @Id
    private String code;
    @NotNull
    private String name;
    @NotNull
    private int weight;
    @NotNull
    private String imageURL;

    public Medication() {
    }

    public Medication(String code, String name, int weight, String imageURL) {
        this.code = code;
        this.name = name;
        this.weight = weight;
        this.imageURL = imageURL;
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

    @Override
    public String toString() {
        return "Medication code: " + this.getCode() + " name: " + this.getName() + " Weight: " + this.getWeight() + " imageURL: " + this.getImageURL();
    }
}
