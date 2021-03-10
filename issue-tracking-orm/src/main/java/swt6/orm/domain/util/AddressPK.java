package swt6.orm.domain.util;

import javax.persistence.Id;
import java.io.Serializable;

public class AddressPK implements Serializable {
    private String zipCode;
    private String city;
    private String street;

    public AddressPK() {
    }

    public AddressPK(String zipCode, String city, String street) {
        this.zipCode = zipCode;
        this.city = city;
        this.street = street;
    }
}
