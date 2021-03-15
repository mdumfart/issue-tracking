package orm.tests;

import org.junit.Assert;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swt6.orm.dao.implementation.JpaAddressDao;
import swt6.orm.dao.interfaces.AddressDao;
import swt6.orm.domain.Address;
import swt6.orm.domain.util.AddressPK;
import swt6.util.JpaUtil;

import javax.annotation.Resource;

public class AddressDaoTests {
    @Resource
    private final AddressDao addressDao = new JpaAddressDao();

    @BeforeEach
    public void openTransaction() {
        JpaUtil.openTransaction();
    }

    @AfterEach
    public void closeTransaction() {
        JpaUtil.rollback();
    }

    @Test
    public void createAddress_WhenSave_GetSuccessful() {
        Address address = new Address("4190", "Bad Leonfelden", "Salzstrasse", "24b");

        address = addressDao.create(address);

        Assertions.assertEquals(address, addressDao.find(
                new AddressPK(address)
        ));
    }

    @Test
    public void createAddress_WhenDuplicate_CreateIgnoredGetSuccessful() {
        Address address1 = new Address("4030", "Linz", "Landstrasse", "16");
        Address address2 = new Address("4030", "Linz", "Landstrasse", "16");

        address1 = addressDao.create(address1);
        address2 = addressDao.create(address2);

        Assertions.assertEquals(address1, address2);

        Assertions.assertEquals(address2, addressDao.find(
                new AddressPK(address2)
        ));
        Assertions.assertEquals(address1, addressDao.find(
                new AddressPK(address1)
        ));

    }

    @Test
    public void deleteAddress_WhenDelete_GetUnsuccessful() {
        Address address = new Address("4040", "Linz", "Promenade", "18");

        address = addressDao.create(address);

        Assertions.assertEquals(address, addressDao.find(
                new AddressPK(address)
        ));

        addressDao.delete(address);

        Assertions.assertNull(addressDao.find(
                new AddressPK(address)
        ));
    }

    @Test
    public void deleteAddress_WhenNull_ThrowsException() {
        Assertions.assertThrows(Exception.class, () -> addressDao.delete(null));
    }

    @Test
    public void findById_WhenNotFound_IsNull() {
        Assert.assertNull(addressDao.find(
                new AddressPK(
                        "9999",
                        "TestCityThatDoesNotExist",
                        "StreetThatDoesNotExist",
                        "9999z")
        ));
    }
}
