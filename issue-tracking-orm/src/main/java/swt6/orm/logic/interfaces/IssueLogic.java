package swt6.orm.logic.interfaces;

import swt6.orm.domain.Employee;
import swt6.orm.domain.Issue;
import swt6.orm.domain.Project;

import java.util.Set;

public interface IssueLogic {
    Issue create(Issue issue);
    Issue update(Issue issue);
    void delete(Issue issue);
    Issue findById(int id);
    Set<Issue> findIssuesByProject(Project project);

    /**
     * Gets the invested time of "resolved" issues in a project, assigned to a certain employee
     * @param employee
     * @param project
     */
    double getInvestedTimeByEmployeeAndProject(Employee employee, Project project);

    /**
     * Gets the time to invest to do all the "open" issues in a project, assigned to a certain employee
     * @param employee
     * @param project
     */
    double getTimeToInvestByEmployeeInProject(Employee employee, Project project);
}
