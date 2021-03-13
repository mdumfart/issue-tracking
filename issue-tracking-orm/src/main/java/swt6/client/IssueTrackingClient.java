package swt6.client;

import swt6.client.util.ClientUtil;
import swt6.orm.dao.implementation.JpaEmployeeDao;
import swt6.orm.dao.implementation.JpaIssueDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.PermanentEmployee;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;
import swt6.orm.logic.factory.IssueTrackingLogicFactory;
import swt6.orm.logic.factory.JpaIssueTrackingLogicFactory;
import swt6.orm.logic.implementation.EmployeeLogicImpl;
import swt6.orm.logic.implementation.IssueLogicImpl;
import swt6.orm.logic.interfaces.EmployeeLogic;
import swt6.orm.logic.interfaces.IssueLogic;

import java.time.LocalDate;

public class IssueTrackingClient {
    private static final IssueTrackingLogicFactory logicFactory = new JpaIssueTrackingLogicFactory();

    private static final EmployeeLogic employeeLogic = logicFactory.getEmployeeLogic();
    private static final IssueLogic issueLogic = new IssueLogicImpl(new JpaIssueDao());

    private static void testEmployee() {
        ClientUtil.printHeader("Testing Employee");

        ClientUtil.printSubHeader("Create:");

        PermanentEmployee permanentEmployee1 = new PermanentEmployee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));
        permanentEmployee1.setSalary(5000.0);
        Employee employee1 = permanentEmployee1;
        employee1 = employeeLogic.create(employee1);

        System.out.println(employee1.toString());

        ClientUtil.printSubHeader("Update:");

        employee1.setDateOfBirth(LocalDate.of(1969, 1, 1));
        employee1 = employeeLogic.update(employee1);

        System.out.println(employee1.toString());

        ClientUtil.printSubHeader("Find by id:");

        System.out.println(employeeLogic.findById(employee1.getId()).toString());

        ClientUtil.printDivider();
    }

    private static void testProject() {

    }

    public static void main(String[] args) {
        testEmployee();
    }
}
