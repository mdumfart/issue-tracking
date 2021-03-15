package swt6.orm.logic.implementation;

import swt6.orm.dao.interfaces.EmployeeDao;
import swt6.orm.dao.interfaces.IssueDao;
import swt6.orm.dao.interfaces.ProjectDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssueState;
import swt6.orm.logic.interfaces.IssueLogic;
import swt6.util.JpaUtil;

import java.time.temporal.ChronoUnit;
import java.util.HashSet;
import java.util.Set;

public class IssueLogicImpl implements IssueLogic {
    private final IssueDao issueDao;
    private final ProjectDao projectDao;
    private final EmployeeDao employeeDao;

    public IssueLogicImpl(IssueDao issueDao, ProjectDao projectDao, EmployeeDao employeeDao) {
        this.issueDao = issueDao;
        this.projectDao = projectDao;
        this.employeeDao = employeeDao;
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
    public Issue findById(long id) {
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

    @Override
    public Set<Issue> findIssuesByProject(Project project) {
        Set<Issue> issues = new HashSet<>();

        try {
            JpaUtil.openTransaction();

            Project p = projectDao.findById(project.getId());

            if (p != null) {
                issues = p.getIssues();
            }

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return issues;
    }

    @Override
    public double getInvestedTimeByEmployeeAndProject(Employee employee, Project project) {
        Set<Issue> issues = null;

        try {
            JpaUtil.openTransaction();

            Project p = projectDao.findById(project.getId());
            Employee e = employeeDao.findById(employee.getId());

            if (p != null && e != null) {
                issues = issueDao.findIssuesByProjectEmployeeAndState(p, e, IssueState.resolved);
            }

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return getTimeFromIssues(issues);
    }

    @Override
    public double getInvestedTimeByProjectAndState(Project project, IssueState state) {
        Set<Issue> issues = null;

        try {
            JpaUtil.openTransaction();

            Project p = projectDao.findById(project.getId());

            if (p != null) {
                issues = issueDao.findIssuesByProjectAndState(p, state);
            }

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return getTimeFromIssues(issues);
    }

    @Override
    public double getTimeToInvestByEmployeeInProject(Employee employee, Project project) {
        Set<Issue> issues = null;

        try {
            JpaUtil.openTransaction();

            Project p = projectDao.findById(project.getId());
            Employee e = employeeDao.findById(employee.getId());

            if (p != null && e != null) {
                issues = issueDao.findIssuesByProjectEmployeeAndState(p, e, IssueState.open);
            }

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        double timeSum = 0.0;

        if (issues != null) {
            for(Issue i : issues) {
                timeSum += i.getEstimatedTime();
            }
        }

        return timeSum;
    }

    @Override
    public Set<Issue> findIssuesByEmployeeAndState(Employee employee, IssueState state) {
        Set<Issue> issues = null;

        try {
            JpaUtil.openTransaction();

            Employee e = employeeDao.findById(employee.getId());

            if (e != null) {
                issues = issueDao.findIssuesByEmployeeAndState(employee, state);
            }

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return issues;
    }

    @Override
    public double getTimeInvestedByEmployeeAndState(Employee employee, IssueState state) {
        Set<Issue> issues = findIssuesByEmployeeAndState(employee, state);

        return getTimeFromIssues(issues);
    }

    private double getTimeFromIssues(Set<Issue> issues) {
        double timeSum = 0.0;

        if (issues != null) {
            for(Issue i : issues) {
                for (LogbookEntry lbe: i.getLogbookEntries()) {
                    timeSum += ChronoUnit.MINUTES.between(lbe.getStartTime(), lbe.getEndTime()) / 60d;
                }
            }
        }

        return timeSum;
    }
}
