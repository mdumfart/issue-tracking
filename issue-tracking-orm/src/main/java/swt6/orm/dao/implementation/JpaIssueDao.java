package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.IssueDao;
import swt6.orm.domain.Issue;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class JpaIssueDao implements IssueDao {
    @Override
    public Issue create(Issue issue) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.merge(issue);
    }

    @Override
    public Issue update(Issue issue) {
        return JpaUtil.updateEntity(issue.getId(), issue, Issue.class);
    }

    @Override
    public void delete(Issue issue) {
        EntityManager em = JpaUtil.getEntityManager();

        em.remove(issue);
    }

    @Override
    public Issue findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.find(Issue.class, id);
    }
}
