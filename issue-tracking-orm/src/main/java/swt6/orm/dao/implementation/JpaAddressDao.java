package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.AddressDao;
import swt6.orm.domain.Address;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class JpaAddressDao implements AddressDao {
    @Override
    public Address create(Address address) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.merge(address);
    }

    @Override
    public void delete(Address address) {
        EntityManager em = JpaUtil.getEntityManager();

        em.remove(address);
    }
}
