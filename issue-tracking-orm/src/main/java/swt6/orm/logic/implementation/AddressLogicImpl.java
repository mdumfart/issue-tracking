package swt6.orm.logic.implementation;

import swt6.orm.dao.interfaces.AddressDao;
import swt6.orm.domain.Address;
import swt6.orm.logic.interfaces.AddressLogic;
import swt6.util.JpaUtil;

public class AddressLogicImpl implements AddressLogic {
    private final AddressDao addressDao;

    public AddressLogicImpl(AddressDao addressDao) {
        this.addressDao = addressDao;
    }

    @Override
    public Address create(Address address) {
        Address createdAddress = null;

        try {
            JpaUtil.openTransaction();

            createdAddress = addressDao.create(address);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return createdAddress;
    }

    @Override
    public void delete(Address address) {
        try {
            JpaUtil.openTransaction();

            addressDao.delete(address);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }
}
