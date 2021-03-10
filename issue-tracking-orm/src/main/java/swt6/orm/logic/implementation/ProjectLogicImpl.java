package swt6.orm.logic.implementation;

import swt6.orm.dao.interfaces.ProjectDao;
import swt6.orm.domain.Project;
import swt6.orm.logic.interfaces.ProjectLogic;
import swt6.util.JpaUtil;

public class ProjectLogicImpl implements ProjectLogic {
    private final ProjectDao projectDao;

    public ProjectLogicImpl(ProjectDao projectDao) {
        this.projectDao = projectDao;
    }

    @Override
    public Project create(Project project) {
        Project createdProject = null;

        try {
            JpaUtil.openTransaction();

            createdProject = projectDao.create(project);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return createdProject;
    }

    @Override
    public Project update(Project project) {
        Project updatedProject = null;

        try {
            JpaUtil.openTransaction();

            updatedProject = projectDao.update(project);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return updatedProject;
    }

    @Override
    public void delete(Project project) {
        try {
            JpaUtil.openTransaction();

            projectDao.delete(project);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public Project findById(int id) {
        Project project = null;

        try {
            JpaUtil.openTransaction();

            project = projectDao.findById(id);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return project;
    }
}
