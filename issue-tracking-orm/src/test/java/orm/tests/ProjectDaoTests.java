package orm.tests;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import swt6.orm.dao.implementation.JpaProjectDao;
import swt6.orm.dao.interfaces.ProjectDao;
import swt6.orm.domain.Issue;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;
import swt6.util.JpaUtil;

import javax.annotation.Resource;

public class ProjectDaoTests {
    @Resource
    private static final ProjectDao projectDao = new JpaProjectDao();

    @BeforeEach
    public void openTransaction() {
        JpaUtil.openTransaction();
    }

    @AfterEach
    public void closeTransaction() {
        JpaUtil.rollback();
    }

    @Test
    public void createProject_WhenCreate_GetSuccessful() {
        Project project = getTestProject();

        project = projectDao.create(project);

        Assertions.assertEquals(project, projectDao.findById(project.getId()));
    }

    @Test
    public void createProject_WhenNameNull_ThrowsException() {
        Project project = new Project(null);

        Assertions.assertThrows(Exception.class, () -> projectDao.create(project));
    }

    @Test
    public void updateProject_WhenUpdate_GetSuccessful() {
        Project project = getTestProject();
        String updatedName = "updatedProjectName";

        project = projectDao.create(project);

        project.setName(updatedName);
        project = projectDao.update(project);

        Assertions.assertEquals(project, projectDao.findById(project.getId()));
    }

    @Test
    public void deleteProject_WhenDelete_GetUnsuccessful() {
        Project project = getTestProject();

        project = projectDao.create(project);

        Assertions.assertEquals(project, projectDao.findById(project.getId()));

        projectDao.delete(project);

        Assertions.assertNull(projectDao.findById(project.getId()));
    }

    @Test
    public void deleteProject_WhenNull_ThrowsException() {
        Assertions.assertThrows(Exception.class, () -> projectDao.delete(null));
    }

    private Project getTestProject() {
        return new Project("testProject");
    }
}
