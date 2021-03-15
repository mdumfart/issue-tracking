package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.IssueDao;
import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.Issue_;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssueState;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
    public Issue findById(long id) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.find(Issue.class, id);
    }

    @Override
    public Set<Issue> findIssuesByProjectEmployeeAndState(Project project, Employee employee, IssueState state) {
        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Issue> issueCQ = cb.createQuery(Issue.class);
        Root<Issue> issue = issueCQ.from(Issue.class);

        ParameterExpression<Project> peProject = cb.parameter(Project.class);
        ParameterExpression<Employee> peEmployee = cb.parameter(Employee.class);
        ParameterExpression<IssueState> peState = cb.parameter(IssueState.class);


        issueCQ.where(cb.equal(issue.get(Issue_.STATE), peState),
                cb.equal(issue.get(Issue_.PROJECT), peProject),
                cb.equal(issue.get(Issue_.EMPLOYEE), peEmployee)).select(issue).orderBy(cb.asc(issue.get(Issue_.ID)));

        TypedQuery<Issue> entriesQry = em.createQuery(issueCQ);
        entriesQry.setParameter(peProject, project);
        entriesQry.setParameter(peEmployee, employee);
        entriesQry.setParameter(peState, state);

        return new HashSet<>(entriesQry.getResultList());
    }

    @Override
    public Set<Issue> findIssuesByProjectAndState(Project project, IssueState state) {
        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Issue> issueCQ = cb.createQuery(Issue.class);
        Root<Issue> issue = issueCQ.from(Issue.class);

        ParameterExpression<Project> peProject = cb.parameter(Project.class);
        ParameterExpression<IssueState> peState = cb.parameter(IssueState.class);


        issueCQ.where(cb.equal(issue.get(Issue_.STATE), peState),
                cb.equal(issue.get(Issue_.PROJECT), peProject)).select(issue).orderBy(cb.asc(issue.get(Issue_.ID)));

        TypedQuery<Issue> entriesQry = em.createQuery(issueCQ);
        entriesQry.setParameter(peProject, project);
        entriesQry.setParameter(peState, state);

        return new HashSet<>(entriesQry.getResultList());
    }

    @Override
    public Set<Issue> findIssuesByEmployeeAndState(Employee employee, IssueState state) {
        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Issue> issueCQ = cb.createQuery(Issue.class);
        Root<Issue> issue = issueCQ.from(Issue.class);

        ParameterExpression<Employee> peEmployee = cb.parameter(Employee.class);
        ParameterExpression<IssueState> peState = cb.parameter(IssueState.class);

        issueCQ.where(cb.equal(issue.get(Issue_.EMPLOYEE), peEmployee),
                cb.equal(issue.get(Issue_.STATE), peState)).select(issue).orderBy(cb.asc(issue.get(Issue_.ID)));

        TypedQuery<Issue> entriesQry = em.createQuery(issueCQ);
        entriesQry.setParameter(peEmployee, employee);
        entriesQry.setParameter(peState, state);

        return new HashSet<>(entriesQry.getResultList());
    }

    @Override
    public Set<Issue> findAll() {
        EntityManager em = JpaUtil.getEntityManager();

        return new HashSet<>(
                em.createQuery("Select issue from Issue issue order by issue.id", Issue.class).getResultList()
        );
    }

    @Override
    public Set<Issue> findIssuesByEmployeeAndProject(Employee e, Project p) {
        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Issue> issueCQ = cb.createQuery(Issue.class);
        Root<Issue> issue = issueCQ.from(Issue.class);

        ParameterExpression<Employee> peEmployee = cb.parameter(Employee.class);
        ParameterExpression<Project> peProject = cb.parameter(Project.class);

        issueCQ.where(cb.equal(issue.get(Issue_.EMPLOYEE), peEmployee),
                cb.equal(issue.get(Issue_.PROJECT), peProject)).select(issue).orderBy(cb.asc(issue.get(Issue_.ID)));

        TypedQuery<Issue> entriesQry = em.createQuery(issueCQ);
        entriesQry.setParameter(peEmployee, e);
        entriesQry.setParameter(peProject, p);

        return new HashSet<>(entriesQry.getResultList());
    }
}
