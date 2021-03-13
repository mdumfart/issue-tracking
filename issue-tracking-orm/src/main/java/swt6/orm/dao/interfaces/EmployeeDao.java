package swt6.orm.dao.interfaces;

import swt6.orm.domain.Employee;

public interface EmployeeDao {
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Employee employee);
    Employee findById(long id);
}
