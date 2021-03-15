package swt6.client;

import swt6.client.util.ClientUtil;
import swt6.orm.dao.implementation.JpaEmployeeDao;
import swt6.orm.dao.implementation.JpaIssueDao;
import swt6.orm.domain.*;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;
import swt6.orm.logic.factory.IssueTrackingLogicFactory;
import swt6.orm.logic.factory.JpaIssueTrackingLogicFactory;
import swt6.orm.logic.implementation.EmployeeLogicImpl;
import swt6.orm.logic.implementation.IssueLogicImpl;
import swt6.orm.logic.interfaces.AddressLogic;
import swt6.orm.logic.interfaces.EmployeeLogic;
import swt6.orm.logic.interfaces.IssueLogic;
import swt6.orm.logic.interfaces.ProjectLogic;
import swt6.util.JpaUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class IssueTrackingClient {
    private static final IssueTrackingLogicFactory logicFactory = new JpaIssueTrackingLogicFactory();

    private static final EmployeeLogic employeeLogic = logicFactory.getEmployeeLogic();
    private static final IssueLogic issueLogic = logicFactory.getIssueLogic();
    private static final ProjectLogic projectLogic = logicFactory.getProjectLogic();
    private static final AddressLogic addressLogic = logicFactory.getAddressLogic();

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
        ClientUtil.printHeader("Testing Project");

        ClientUtil.printSubHeader("Create:");
        Project project1 = new Project("Apollo");

        project1 = projectLogic.create(project1);
        Project project2 = projectLogic.create(new Project("Orion"));

        System.out.println(project1.toString());

        ClientUtil.printSubHeader("Create some Issues:");
        Issue issue1 = issueLogic.create(new Issue("Implement DAOs", IssueState.open, IssuePriority.low, 1.5, 0.6, project1));
        Issue issue2 = issueLogic.create(new Issue("Drink coffee", IssueState.resolved, IssuePriority.low, 3.5, 1, project1));
        Issue issue3 = issueLogic.create(new Issue("Drink some more coffee", IssueState.resolved, IssuePriority.high, 5, 0.0, project1));
        Issue issue4 = issueLogic.create(new Issue("Drink some more coffee", IssueState.resolved, IssuePriority.high, 5, 0.0, project2));
        Issue issue5 = issueLogic.create(new Issue("Implement some functionality", IssueState.open, IssuePriority.high, 4, 0.0, project2));

        ClientUtil.printSubHeader("Issues of " + project1.toString());

        for(Issue i : issueLogic.findIssuesByProject(project1)) {
            System.out.println(i.toString());
        }

        ClientUtil.printSubHeader("Add Issues to employee and Add LogbookEntry to Issue:");
        Employee employee1 = employeeLogic.findById(1);

        issue2.addLogbookEntry(new LogbookEntry(
                        "test",
                        LocalDateTime.of(2021, 03, 1, 12, 0),
                        LocalDateTime.of(2021, 03, 1, 14, 30)
                )
        );
        issue2.addLogbookEntry(new LogbookEntry(
                        "test1",
                        LocalDateTime.of(2021, 03, 1, 15, 0),
                        LocalDateTime.of(2021, 03, 1, 17, 0)
                )
        );
        issueLogic.update(issue2);

        issue4.addLogbookEntry(new LogbookEntry(
                        "test",
                        LocalDateTime.of(2021, 03, 1, 11, 0),
                        LocalDateTime.of(2021, 03, 1, 13, 40)
                )
        );
        issue4.addLogbookEntry(new LogbookEntry(
                        "test1",
                        LocalDateTime.of(2021, 03, 1, 14, 0),
                        LocalDateTime.of(2021, 03, 1, 17, 0)
                )
        );
        issueLogic.update(issue2);


        for(Issue i : issueLogic.findIssuesByProject(project1)) {
            employee1.addIssue(i);
        }
        employeeLogic.update(employee1);

        for(Issue i : issueLogic.findIssuesByProject(project2)) {
            employee1.addIssue(i);
        }
        employeeLogic.update(employee1);

        ClientUtil.printSubHeader("Time spent on resolved issues in Apollo Project by Michael Dumfart:");
        System.out.println(issueLogic.getInvestedTimeByEmployeeAndProject(employee1, project1));

        ClientUtil.printSubHeader("Time to spend on open issues in Apollo Project by Michael Dumfart:");
        System.out.println(issueLogic.getTimeToInvestByEmployeeInProject(employee1, project1));
    }

    public static void main(String[] args) {
        testEmployee();
        testProject();
    }
}
