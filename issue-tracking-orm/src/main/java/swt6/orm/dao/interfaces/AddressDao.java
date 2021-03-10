package swt6.orm.dao.interfaces;


import swt6.orm.domain.Address;

public interface AddressDao {
    Address create(Address address);
    void delete(Address address);
}
