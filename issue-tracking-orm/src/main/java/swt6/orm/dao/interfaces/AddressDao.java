package swt6.orm.dao.interfaces;


import swt6.orm.domain.Address;
import swt6.orm.domain.util.AddressPK;

public interface AddressDao {
    Address create(Address address);
    void delete(Address address);
    Address find(AddressPK address);
}
