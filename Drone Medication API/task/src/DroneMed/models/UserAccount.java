package DroneMed.models;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;

@Entity
public class UserAccount {

    @NotNull
    @Enumerated(EnumType.STRING)
    private Account account;

    @NotNull
    private String name;

    @Id
    private String phoneNumber;

    @NotNull
    private String address;

    @NotNull
    private String gpsCoordinate;

    public UserAccount() {
    }

    public UserAccount(Account account, String name, String phoneNumber, String address, String gpsCoordinate) {
        this.account = account;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.address = address;
        this.gpsCoordinate = gpsCoordinate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getGpsCoordinate() {
        return gpsCoordinate;
    }

    public void setGpsCoordinate(String gpsCoordinate) {
        this.gpsCoordinate = gpsCoordinate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    @Override
    public String toString() {
        return "UserAccount name: " + this.getName()+ " phoneNumber: " + this.getPhoneNumber() + " address: " + this.getAddress() + " account: " + this.getAccount();
    }
}
