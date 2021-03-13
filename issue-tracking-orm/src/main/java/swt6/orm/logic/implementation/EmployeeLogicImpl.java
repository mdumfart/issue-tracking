package swt6.orm.logic.implementation;

import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.logic.interfaces.EmployeeLogic;
import swt6.util.JpaUtil;

public class EmployeeLogicImpl implements EmployeeLogic {
    private final EmployeeDao employeeDao;

    public EmployeeLogicImpl(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    @Override
    public Employee create(Employee employee) {
        Employee createdEmployee = null;

        try {
            JpaUtil.openTransaction();

            createdEmployee = employeeDao.create(employee);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return createdEmployee;
    }

    @Override
    public Employee update(Employee employee) {
        Employee updatedEmployee = null;

        try {
            JpaUtil.openTransaction();

            updatedEmployee = employeeDao.update(employee);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return updatedEmployee;
    }

    @Override
    public void delete(Employee employee) {
        try {
            JpaUtil.openTransaction();

            employeeDao.delete(employee);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public Employee findById(long id) {
        Employee employee = null;

        try {
            JpaUtil.openTransaction();

            employee = employeeDao.findById(id);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return employee;
    }
}
