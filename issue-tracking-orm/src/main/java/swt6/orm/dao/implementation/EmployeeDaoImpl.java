package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.domain.Employee;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class EmployeeDaoImpl implements EmployeeDao {
    @Override
    public Employee create(Employee employee) {
        return JpaUtil.saveEntity(employee);
    }

    @Override
    public Employee update(Employee employee) {
        return JpaUtil.updateEntity(employee.getId(), employee, Employee.class);
    }

    @Override
    public void delete(Employee employee) {
        JpaUtil.removeEntity(employee);
    }

    @Override
    public Employee findById(int id) {
        Employee employee = null;

        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();

            employee = em.find(Employee.class, id);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return employee;
    }
}
