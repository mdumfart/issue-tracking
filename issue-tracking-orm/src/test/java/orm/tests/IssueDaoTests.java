package orm.tests;

import org.junit.Assert;
import org.junit.jupiter.api.*;
import swt6.orm.dao.implementation.JpaEmployeeDao;
import swt6.orm.dao.implementation.JpaIssueDao;
import swt6.orm.dao.implementation.JpaProjectDao;
import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.dao.interfaces.IssueDao;
import swt6.orm.dao.interfaces.ProjectDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;
import swt6.util.JpaUtil;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.util.Set;

public class IssueDaoTests {
    @Resource
    private static final IssueDao issueDao = new JpaIssueDao();
    @Resource
    private static final ProjectDao projectDao = new JpaProjectDao();
    @Resource
    private static final EmployeeDao employeeDao = new JpaEmployeeDao();

    private static Project complexTestProject1;
    private static Project complexTestProject2;
    private static Issue complexTestIssue1;
    private static Issue complexTestIssue2;
    private static Issue complexTestIssue3;
    private static Issue complexTestIssue4;
    private static Issue complexTestIssue5;
    private static Employee complexTestEmployee1;
    private static Employee complexTestEmployee2;

    @BeforeEach
    public void openTransaction() {
        JpaUtil.openTransaction();
    }

    @AfterEach
    public void closeTransaction() {
        JpaUtil.rollback();
    }

    @BeforeAll
    public static void createTestData() {
        JpaUtil.openTransaction();
        // create projects
        complexTestProject1 = projectDao.create(new Project("complexTestProject1"));
        complexTestProject2 = projectDao.create(new Project("complexTestProject2"));

        // create issues
        complexTestIssue1 = issueDao.create(
                new Issue("complexTestIssue1", IssueState.nev, IssuePriority.low, 0, complexTestProject1)
        );

        complexTestIssue2 = issueDao.create(
                new Issue("complexTestIssue2", IssueState.open, IssuePriority.low, 0.5, complexTestProject1)
        );

        complexTestIssue3 = issueDao.create(
                new Issue("complexTestIssue3", IssueState.nev, IssuePriority.high, 0.1, complexTestProject1)
        );

        complexTestIssue4 = issueDao.create(
                new Issue("complexTestIssue4", IssueState.open, IssuePriority.high, 0, complexTestProject2)
        );

        complexTestIssue5 = issueDao.create(
                new Issue("complexTestIssue5", IssueState.closed, IssuePriority.high, 0, complexTestProject2)
        );

        // create employees
        complexTestEmployee1 = employeeDao.create(new Employee("Klaus", "Mayr", LocalDate.of(1990, 1, 1)));
        complexTestEmployee2 = employeeDao.create(new Employee("Sandra", "Seefahrer", LocalDate.of(1960, 12, 31)));

        // assign employees to issues
        complexTestIssue1.setEmployee(complexTestEmployee1);
        issueDao.update(complexTestIssue1);

        complexTestIssue2.setEmployee(complexTestEmployee1);
        issueDao.update(complexTestIssue2);

        complexTestIssue3.setEmployee(complexTestEmployee2);
        issueDao.update(complexTestIssue3);

        complexTestIssue4.setEmployee(complexTestEmployee1);
        issueDao.update(complexTestIssue4);

        complexTestIssue5.setEmployee(complexTestEmployee2);
        issueDao.update(complexTestIssue5);

        complexTestProject1 = projectDao.update(complexTestProject1);
        complexTestProject2 = projectDao.update(complexTestProject2);
        JpaUtil.commit();
    }

    @Test
    public void createIssue_WhenSave_GetSuccessful() {
        Issue issue = new Issue("testIssue1", IssueState.open, IssuePriority.high, 0, new Project("testProject1"));

        issue = issueDao.create(issue);

        Assertions.assertEquals(issue, issueDao.findById(issue.getId()));
        Assertions.assertEquals(issue.getProject(), issueDao.findById(issue.getId()).getProject());
    }

    @Test
    public void createIssue_WhenIssueNull_ThrowsException() {
        Issue issue = new Issue("testIssue1", null, IssuePriority.high, 0, null);

        Assertions.assertThrows(Exception.class, () -> issueDao.create(issue));
    }

    @Test
    public void createIssue_WhenPriorityNull_ThrowsException() {
        Issue issue = new Issue("testIssue1", IssueState.open, null, 0, null);

        Assertions.assertThrows(Exception.class, () -> issueDao.create(issue));
    }

    @Test
    public void updateIssue_WhenUpdate_GetSuccessful() {
        Issue issue = new Issue("testIssue1", IssueState.nev, IssuePriority.low, 0, new Project("testProject1"));
        String updatedName = "testIssue2";
        IssueState updatedIssueState = IssueState.closed;
        IssuePriority updatedIssuePriority = IssuePriority.high;
        Project updatedProject = new Project("testProject2");

        issue = issueDao.create(issue);

        Assertions.assertEquals(issue, issueDao.findById(issue.getId()));

        issue.setName(updatedName);
        issue.setState(updatedIssueState);
        issue.setPriority(updatedIssuePriority);
        issue.setProject(updatedProject);

        issue = issueDao.update(issue);

        Issue persistedIssue = issueDao.findById(issue.getId());

        Assertions.assertEquals(issue, persistedIssue);
        Assertions.assertEquals(issue.getName(), updatedName);
        Assertions.assertEquals(issue.getState(), updatedIssueState);
        Assertions.assertEquals(issue.getPriority(), updatedIssuePriority);
        Assertions.assertEquals(issue.getProject().getName(), updatedProject.getName());
    }

    @Test
    public void deleteIssue_WhenDelete_GetUnsuccessful() {
        Issue issue = new Issue("testIssue1", IssueState.open, IssuePriority.high, 0, new Project("testProject1"));

        issue = issueDao.create(issue);

        Assertions.assertEquals(issue, issueDao.findById(issue.getId()));

        issueDao.delete(issue);

        Assertions.assertNull(issueDao.findById(issue.getId()));
    }

    @Test
    public void deleteIssue_WhenNull_ThrowsException() {
        Assert.assertThrows(Exception.class, () -> issueDao.delete(null));
    }

    @Test
    public void findById_WhenNotFound_IsNull() {
        Assert.assertNull(issueDao.findById(Long.MAX_VALUE));
    }

    @Test
    public void findIssuesByProjectEmployeeAndState_WhenExist_Success() {
        Project project = complexTestProject1;
        Employee employee = complexTestEmployee1;
        IssueState state = IssueState.nev;

        Set<Issue> issues = issueDao.findIssuesByProjectEmployeeAndState(project, employee, state);

        Assertions.assertTrue(issues.stream().allMatch(
                i -> i.getEmployee().equals(employee) &&
                        i.getProject().equals(project) &&
                        i.getState().equals(state)
        ));

        Assertions.assertTrue(issues.size() > 0);
    }

    @Test
    public void findIssuesByProjectEmployeeAndState_WhenNotExist_Empty() {
        Project project = complexTestProject2;
        Employee employee = complexTestEmployee2;
        IssueState state = IssueState.nev;

        Set<Issue> issues = issueDao.findIssuesByProjectEmployeeAndState(project, employee, state);

        Assertions.assertTrue(issues.size() == 0);
    }

    @Test
    public void findIssuesByProjectAndState_WhenExist_Success() {
        Project project = complexTestProject1;
        IssueState state = IssueState.nev;

        Set<Issue> issues = issueDao.findIssuesByProjectAndState(project, state);

        Assertions.assertTrue(issues.stream().allMatch(
                i -> i.getProject().equals(project) &&
                        i.getState().equals(state)
        ));

        Assertions.assertTrue(issues.size() > 0);
    }

    @Test
    public void findIssuesByProjectAndState_WhenNotExist_Empty() {
        Project project = complexTestProject2;
        IssueState state = IssueState.nev;

        Set<Issue> issues = issueDao.findIssuesByProjectAndState(project, state);

        Assertions.assertTrue(issues.size() == 0);
    }

    @Test
    public void findIssuesByEmployeeAndState_WhenExist_Success() {
        Employee employee = complexTestEmployee1;
        IssueState state = IssueState.open;

        Set<Issue> issues = issueDao.findIssuesByEmployeeAndState(employee, state);

        Assertions.assertTrue(issues.stream().allMatch(
                i -> i.getEmployee().equals(employee) &&
                        i.getState().equals(state)
        ));

        Assertions.assertTrue(issues.size() > 0);
    }

    @Test
    public void findIssuesByEmployeeAndState_WhenNotExist_Empty() {
        Employee employee = complexTestEmployee2;
        IssueState state = IssueState.rejected;

        Set<Issue> issues = issueDao.findIssuesByEmployeeAndState(employee, state);

        Assertions.assertTrue(issues.size() == 0);
    }
}
