package swt6.orm.dao.interfaces;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.Project;
import swt6.orm.domain.util.IssueState;

import java.util.Set;

public interface IssueDao {
    Issue create(Issue issue);
    Issue update(Issue issue);
    void delete(Issue issue);
    Issue findById(long id);
    Set<Issue> findIssuesByProjectEmployeeAndState(Project project, Employee employee, IssueState state);
    Set<Issue> findIssuesByProjectAndState(Project project, IssueState state);
    Set<Issue> findIssuesByEmployeeAndState(Employee employee, IssueState state);
    Set<Issue> findAll();
}
