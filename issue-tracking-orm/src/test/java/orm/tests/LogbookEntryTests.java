package orm.tests;

import org.junit.jupiter.api.*;
import swt6.orm.dao.implementation.JpaEmployeeDao;
import swt6.orm.dao.implementation.JpaIssueDao;
import swt6.orm.dao.implementation.JpaLogbookEntryDao;
import swt6.orm.dao.implementation.JpaProjectDao;
import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.dao.interfaces.IssueDao;
import swt6.orm.dao.interfaces.LogbookEntryDao;
import swt6.orm.dao.interfaces.ProjectDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;
import swt6.util.JpaUtil;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class LogbookEntryTests {
    @Resource
    private static final LogbookEntryDao logbookEntryDao = new JpaLogbookEntryDao();
    private static final EmployeeDao employeeDao = new JpaEmployeeDao();
    private static final ProjectDao projectDao = new JpaProjectDao();
    private static final IssueDao issueDao = new JpaIssueDao();

    private static Employee testEmployee;
    private static Project testProject;
    private static Issue testIssue;

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

        testEmployee = employeeDao.create(new Employee("Michael", "Dumfart", LocalDate.of(1997, 5, 14)));
        testProject = projectDao.create(new Project("testProject"));
        testIssue = issueDao.create(new Issue("testIssue", IssueState.open, IssuePriority.high, 0, testProject));

        JpaUtil.commit();
    }

    @Test
    public void createLogbookEntry_WhenCreate_GetSuccessful() {
        LogbookEntry logbookEntry = getTestEntry();

        logbookEntry = logbookEntryDao.create(logbookEntry);

        Assertions.assertEquals(logbookEntry, logbookEntryDao.findById(logbookEntry.getId()));
    }

    @Test
    public void createLogbookEntry_WhenEndTimeNull_ThrowsException() {
        LogbookEntry logbookEntry = getTestEntry();
        logbookEntry.setEndTime(null);

        Assertions.assertThrows(Exception.class, () -> logbookEntryDao.create(logbookEntry));
    }

    @Test
    public void createLogbookEntry_WhenStartTimeNull_ThrowsException() {
        LogbookEntry logbookEntry = getTestEntry();
        logbookEntry.setStartTime(null);

        Assertions.assertThrows(Exception.class, () -> logbookEntryDao.create(logbookEntry));
    }

    @Test
    public void createLogbookEntry_WhenActivityNull_ThrowsException() {
        LogbookEntry logbookEntry = getTestEntry();
        logbookEntry.setActivity(null);

        Assertions.assertThrows(Exception.class, () -> logbookEntryDao.create(logbookEntry));
    }

    @Test
    public void updateLogbookEntry_WhenUpdate_GetSuccessful() {
        LogbookEntry logbookEntry = getTestEntry();
        String updatedActivity = "My new activity";
        LocalDateTime updatedStartTime = LocalDateTime.of(2021, 1, 1, 10, 50);
        LocalDateTime updatedEndTime = LocalDateTime.of(2021, 1, 1, 14, 20);

        logbookEntry = logbookEntryDao.create(logbookEntry);
        logbookEntry.setActivity(updatedActivity);
        logbookEntry.setStartTime(updatedStartTime);
        logbookEntry.setEndTime(updatedEndTime);
        logbookEntry.setEmployee(testEmployee);
        logbookEntry.setIssue(testIssue);

        logbookEntryDao.update(logbookEntry);

        Assertions.assertEquals(logbookEntry, logbookEntryDao.findById(logbookEntry.getId()));
        Assertions.assertEquals(logbookEntry.getEmployee(), testEmployee);
        Assertions.assertEquals(logbookEntry.getIssue(), testIssue);
    }

    @Test
    public void deleteLogbookEntry_WhenDelete_GetUnsuccessful() {
        LogbookEntry logbookEntry = getTestEntry();

        logbookEntry = logbookEntryDao.create(logbookEntry);

        Assertions.assertEquals(logbookEntry, logbookEntryDao.findById(logbookEntry.getId()));

        logbookEntryDao.delete(logbookEntry);

        Assertions.assertNull(logbookEntryDao.findById(logbookEntry.getId()));
    }

    @Test
    public void deleteLogbookEntry_WhenNull_ThrowsException() {
        Assertions.assertThrows(Exception.class, () -> logbookEntryDao.delete(null));
    }

    private LogbookEntry getTestEntry() {
        return new LogbookEntry(
                "testEntry1",
                LocalDateTime.of(2021, 3, 15, 8, 30),
                LocalDateTime.of(2021, 3, 15, 10, 0)
        );
    }
}
