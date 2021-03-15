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

    private static Employee employee1;
    private static Employee employee2;
    private static Address address1;

    private static Project project1;
    private static Project project2;

    private static void testEmployee() {
        ClientUtil.printHeader("Create some employee");

        ClientUtil.printSubHeader("First employee:");

        PermanentEmployee permanentEmployee1 = new PermanentEmployee("Michael", "Dumfart", LocalDate.of(1997, 5, 14));
        permanentEmployee1.setSalary(5000.0);
        employee1 = permanentEmployee1;
        employee1 = employeeLogic.create(employee1);

        System.out.println(employee1.toString());

        ClientUtil.printSubHeader("Second employee:");

        TemporaryEmployee temporaryEmployee1 = new TemporaryEmployee("Peter", "Pirklbauer", LocalDate.of(1960, 1, 10));
        temporaryEmployee1.setHourlyRate(15.80);
        temporaryEmployee1.setRenter("Someone");
        employee2 = temporaryEmployee1;
        employee2 = employeeLogic.create(employee2);

        System.out.println(employee2.toString());

        ClientUtil.printHeader("Update lastName of second employee, add new address");

        address1 = new Address("4040", "Linz", "Promenade", "11a");
        address1 = addressLogic.create(address1);

        employee2.setLastName("Pusztakraut");
        employee2.setAddress(address1);

        employee2 = employeeLogic.update(employee2);

        System.out.println(employee2.toString());

        ClientUtil.printSubHeader("Find both employees by id:");
        System.out.println(employeeLogic.findById(employee1.getId()));
        System.out.println(employeeLogic.findById(employee2.getId()));
    }

    private static void testProject() {
        ClientUtil.printHeader("Create some projects");

        ClientUtil.printSubHeader("First project:");
        project1 = new Project("Apollo");

        project1 = projectLogic.create(project1);
        System.out.println(project1.toString());

        ClientUtil.printSubHeader("Second project:");
        project2 = new Project("Orion");

        project2 = projectLogic.create(project2);
        System.out.println(project2.toString());
    }

    private static void testIssues() {

        ClientUtil.printHeader("Create some Issues and add them to the projects");
        Issue issue1 = issueLogic.create(new Issue("Implement DAOs", IssueState.open, IssuePriority.low, 5, 0.6, project1));
        Issue issue2 = issueLogic.create(new Issue("Drink coffee", IssueState.resolved, IssuePriority.low, 3.5, 1, project1));
        Issue issue3 = issueLogic.create(new Issue("Drink some more coffee", IssueState.open, IssuePriority.high, 5, 0.0, project1));
        Issue issue4 = issueLogic.create(new Issue("Find a solution for that obnoxious bug", IssueState.open, IssuePriority.high, 5, 0.0, project2));
        Issue issue5 = issueLogic.create(new Issue("Implement some functionality", IssueState.closed, IssuePriority.high, 4, 1.0, project2));
        Issue issue6 = issueLogic.create(new Issue("Add a nice button", IssueState.open, IssuePriority.low, 1, 0.0, project1));

        ClientUtil.printSubHeader("Issues of project " + project1.toString());

        for(Issue i : issueLogic.findIssuesByProject(project1)) {
            System.out.println(i.toString());
        }

        ClientUtil.printSubHeader("Issues of project " + project2.toString());

        for(Issue i : issueLogic.findIssuesByProject(project2)) {
            System.out.println(i.toString());
        }

        ClientUtil.printHeader("Add Issues to employee and Add LogbookEntry to Issue");

        issue1.setEmployee(employee1);
        issue6.setEmployee(employee1);
        issue1 = issueLogic.update(issue1);
        issue6 = issueLogic.update(issue6);

        issue5.addLogbookEntry(new LogbookEntry(
                "Do some implementing",
                LocalDateTime.of(2021, 03, 1, 10, 0),
                LocalDateTime.of(2021, 03, 1, 16, 30)
        ));
        issue5.setEmployee(employee2);
        issue5 = issueLogic.update(issue5);

        issue2.addLogbookEntry(new LogbookEntry(
                        "Start implementing",
                        LocalDateTime.of(2021, 03, 1, 12, 0),
                        LocalDateTime.of(2021, 03, 1, 14, 30)
                )
        );

        issue2.setEmployee(employee1);

        issue2.addLogbookEntry(new LogbookEntry(
                        "Continue implementing",
                        LocalDateTime.of(2021, 03, 1, 15, 0),
                        LocalDateTime.of(2021, 03, 1, 17, 0)
                )
        );

        issueLogic.update(issue2);

        issue3.setEmployee(employee1);


        issue4.setEmployee(employee2);


        issue4.addLogbookEntry(new LogbookEntry(
                        "Do some work",
                        LocalDateTime.of(2021, 03, 1, 11, 0),
                        LocalDateTime.of(2021, 03, 1, 13, 0)
                )
        );
        issue4.addLogbookEntry(new LogbookEntry(
                        "Finish work",
                        LocalDateTime.of(2021, 03, 1, 14, 0),
                        LocalDateTime.of(2021, 03, 1, 17, 0)
                )
        );

        issueLogic.update(issue4);

        ClientUtil.printSubHeader("Project " + project1.toString() + " now has issues with the following logs:");

        for(Issue i : issueLogic.findIssuesByProject(project1)) {
            printIssueWithLogs(i);
        }

        ClientUtil.printSubHeader("Project " + project2.toString() + " now has issues with the following logs:");

        for(Issue i : issueLogic.findIssuesByProject(project2)) {
            printIssueWithLogs(i);
        }

        ClientUtil.printHeader("Time spent on resolved issues in Apollo Project by Michael Dumfart");
        System.out.println(issueLogic.getInvestedTimeByEmployeeAndProject(employee1, project1));

        ClientUtil.printHeader("Time to spend on open issues in Apollo Project by Michael Dumfart");
        System.out.println(issueLogic.getTimeToInvestByEmployeeInProject(employee1, project1));

        ClientUtil.printHeader("Time spent by Michael Dumfart on closed issues");
        System.out.println(issueLogic.getTimeInvestedByEmployeeAndState(employee1, IssueState.closed));

        ClientUtil.printHeader("Time spent by Peter Puszta on closed issues");
        System.out.println(issueLogic.getTimeInvestedByEmployeeAndState(employee2, IssueState.closed));

        ClientUtil.printHeader("Time spent by Michael Dumfart in Apollo project");
        System.out.println(issueLogic.getInvestedTimeByEmployeeAndProject(employee1, project1));

        ClientUtil.printHeader("Time spent by Peter Puszta in Orion project");
        System.out.println(issueLogic.getInvestedTimeByEmployeeAndProject(employee2, project2));
    }

    private static void printIssueWithLogs(Issue i) {
        System.out.println(i.toString() + ":");

        for(LogbookEntry lbe : i.getLogbookEntries()) {
            System.out.printf("  %s%n", lbe.toString());
        }
    }

    public static void main(String[] args) {
        testEmployee();
        testProject();
        testIssues();
    }
}
