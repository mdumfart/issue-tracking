package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.ProjectDao;
import swt6.orm.domain.Project;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class JpaProjectDao implements ProjectDao {
    @Override
    public Project create(Project project) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.merge(project);
    }

    @Override
    public Project update(Project project) {
        return JpaUtil.updateEntity(project.getId(), project, Project.class);
    }

    @Override
    public void delete(Project project) {
        EntityManager em = JpaUtil.getEntityManager();

        em.remove(project);
    }

    @Override
    public Project findById(long id) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.find(Project.class, id);
    }
}
