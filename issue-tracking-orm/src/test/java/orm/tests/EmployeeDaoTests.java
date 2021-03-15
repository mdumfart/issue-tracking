package orm.tests;

import org.junit.Assert;
import swt6.orm.dao.implementation.JpaEmployeeDao;
import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.domain.Employee;
import org.junit.jupiter.api.*;
import swt6.util.JpaUtil;

import javax.annotation.Resource;
import java.time.LocalDate;

public class EmployeeDaoTests {
    @Resource
    private final EmployeeDao employeeDao = new JpaEmployeeDao();

    @BeforeEach
    public void openTransaction() {
        JpaUtil.openTransaction();
    }

    @AfterEach
    public void closeTransaction() {
        JpaUtil.rollback();
    }

    @Test
    public void createEmployee_WhenSave_GetSuccessful() {
        Employee employee = createTestEmployee();

        employee = employeeDao.create(employee);

        Assertions.assertEquals(employee, employeeDao.findById(employee.getId()));
    }

    @Test void getEmployee_WhenNotExists_ThrowsException() {
        Employee employee = createTestEmployee();

        Assertions.assertThrows(Exception.class, () -> employeeDao.findById(employee.getId()));
    }

    @Test
    public void updateEmployee_WhenUpdate_GetSuccessful() {
        Employee employee = createTestEmployee();

        LocalDate updatedDate = LocalDate.of(1900, 1, 1);
        String updatedName = "Test1";

        employee = employeeDao.create(employee);

        employee.setDateOfBirth(updatedDate);
        employee.setLastName(updatedName);
        employeeDao.update(employee);

        Assertions.assertEquals(employee, employeeDao.findById(employee.getId()));
        Assertions.assertEquals(updatedDate, employeeDao.findById(employee.getId()).getDateOfBirth());
        Assertions.assertEquals(updatedName, employeeDao.findById(employee.getId()).getLastName());
    }

    @Test
    public void updateEmployee_WhenNotExists_ThrowsException() {
        Employee employee = createTestEmployee();


        Assertions.assertThrows(Exception.class, () -> employeeDao.update(employee));
    }

    @Test
    public void deleteEmployee_WhenDelete_GetUnsuccessful() {
        Employee employee = createTestEmployee();

        employee = employeeDao.create(employee);

        Assertions.assertEquals(employee, employeeDao.findById(employee.getId()));

        employeeDao.delete(employee);
        Assertions.assertNull(employeeDao.findById(employee.getId()));
    }

    @Test
    public void deleteEmployee_WhenNull_ThrowsException() {
        Assertions.assertThrows(Exception.class, () -> employeeDao.delete(null));
    }



    private Employee createTestEmployee() {
        return new Employee("Peter", "Test", LocalDate.of(1960, 8, 15));
    }

    @Test
    public void findById_WhenNotFound_IsNull() {
        Assert.assertNull(employeeDao.findById(Long.MAX_VALUE));
    }
}
