package swt6.orm.client;

import swt6.orm.client.util.ClientUtil;
import swt6.orm.dao.implementation.JpaEmployeeDao;
import swt6.orm.dao.implementation.JpaIssueDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;
import swt6.orm.logic.implementation.EmployeeLogicImpl;
import swt6.orm.logic.implementation.IssueLogicImpl;
import swt6.orm.logic.interfaces.EmployeeLogic;
import swt6.orm.logic.interfaces.IssueLogic;

import java.time.LocalDate;

public class IssueTrackingClient {
    private static final EmployeeLogic employeeLogic = new EmployeeLogicImpl(new JpaEmployeeDao());
    private static final IssueLogic issueLogic = new IssueLogicImpl(new JpaIssueDao());

    public static void testEmployee() {
        ClientUtil.printHeader("Testing Employee");

        Employee empl1 = employeeLogic.create(new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14)));
        Issue issue1 = issueLogic.create(new Issue(IssueState.open, IssuePriority.high, 0.1));

        ClientUtil.printDivider();
    }

    public static void main(String[] args) {
        testEmployee();
    }
}
