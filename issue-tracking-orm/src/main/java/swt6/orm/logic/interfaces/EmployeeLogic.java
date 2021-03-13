package swt6.orm.logic.interfaces;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;

public interface EmployeeLogic {
    Employee create(Employee employee);
    Employee update(Employee employee);
    void delete(Employee employee);
    Employee findById(long id);
}
