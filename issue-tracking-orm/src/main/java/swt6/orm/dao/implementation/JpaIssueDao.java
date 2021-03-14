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
    public Issue findById(int id) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.find(Issue.class, id);
    }

    @Override
    public Set<Issue> findIssueByProjectEmployeeAndState(Project project, Employee employee, IssueState state) {
        EntityManager em = JpaUtil.getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();

        CriteriaQuery<Issue> issueCQ = cb.createQuery(Issue.class);
        Root<Issue> issue = issueCQ.from(Issue.class);
        ParameterExpression<Project> peProject = cb.parameter(Project.class);
        ParameterExpression<Employee> peEmployee = cb.parameter(Employee.class);
        ParameterExpression<IssueState> peState = cb.parameter(IssueState.class);

        List<Predicate> predicates = new ArrayList<>();

        predicates.add(cb.equal(issue.get(Issue_.STATE), peState));
        predicates.add(cb.equal(issue.get(Issue_.PROJECT), peProject));
        predicates.add(cb.equal(issue.get(Issue_.EMPLOYEE), peEmployee));

        issueCQ.where(predicates.toArray(new Predicate[]{})).select(issue);

        TypedQuery<Issue> entriesQry = em.createQuery(issueCQ);
        entriesQry.setParameter(peProject, project);
        entriesQry.setParameter(peEmployee, employee);
        entriesQry.setParameter(peState, state);

        Set<Issue> test = new HashSet<>(entriesQry.getResultList());

        return test;
    }
}
