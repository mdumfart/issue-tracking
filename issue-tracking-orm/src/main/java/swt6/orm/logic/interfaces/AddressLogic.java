package swt6.orm.logic.interfaces;


import swt6.orm.domain.Address;

public interface AddressLogic {
    Address create(Address address);
    void delete(Address address);
}
