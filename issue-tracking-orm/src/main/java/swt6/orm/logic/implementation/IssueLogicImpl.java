package swt6.orm.logic.implementation;

import swt6.orm.dao.interfaces.IssueDao;
import swt6.orm.domain.Issue;
import swt6.orm.logic.interfaces.IssueLogic;
import swt6.util.JpaUtil;

public class IssueLogicImpl implements IssueLogic {
    private final IssueDao issueDao;

    public IssueLogicImpl(IssueDao issueDao) {
        this.issueDao = issueDao;
    }

    @Override
    public Issue create(Issue issue) {
        Issue createdIssue = null;

        try {
            JpaUtil.openTransaction();

            createdIssue = issueDao.create(issue);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return createdIssue;
    }

    @Override
    public Issue update(Issue issue) {
        Issue updatedIssue = null;

        try {
            JpaUtil.openTransaction();

            updatedIssue = issueDao.update(issue);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return updatedIssue;
    }

    @Override
    public void delete(Issue issue) {
        try {
            JpaUtil.openTransaction();

            issueDao.delete(issue);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public Issue findById(int id) {
        Issue issue = null;

        try {
            JpaUtil.openTransaction();

            issue = issueDao.findById(id);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return issue;
    }
}
