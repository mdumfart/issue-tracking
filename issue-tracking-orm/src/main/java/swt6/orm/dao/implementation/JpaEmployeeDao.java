package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.domain.Employee;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class JpaEmployeeDao implements EmployeeDao {
    @Override
    public Employee create(Employee employee){
        EntityManager em = JpaUtil.getEntityManager();

        return em.merge(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return JpaUtil.updateEntity(employee.getId(), employee, Employee.class);
    }

    @Override
    public void delete(Employee employee) {
        EntityManager em = JpaUtil.getEntityManager();

        em.remove(employee);
    }

    @Override
    public Employee findById(long id) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.find(Employee.class, id);
    }
}