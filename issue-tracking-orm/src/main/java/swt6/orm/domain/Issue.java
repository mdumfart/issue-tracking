package swt6.orm.domain;

import org.hibernate.annotations.FetchMode;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Issue implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private IssueState state;
    private IssuePriority priority;
    private double estimatedTime;
    private double progress;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToMany(mappedBy = "issues", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Employee> employees = new HashSet<>();

    public Issue() {
    }

    public Issue(IssueState state, IssuePriority priority, double progress) {
        this.state = state;
        this.priority = priority;
        this.progress = progress;
    }

    public Issue(IssueState state, IssuePriority priority, double estimatedTime, double progress) {
        this.state = state;
        this.priority = priority;
        this.estimatedTime = estimatedTime;
        this.progress = progress;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public IssueState getState() {
        return state;
    }

    public void setState(IssueState state) {
        this.state = state;
    }

    public IssuePriority getPriority() {
        return priority;
    }

    public void setPriority(IssuePriority priority) {
        this.priority = priority;
    }

    public double getEstimatedTime() {
        return estimatedTime;
    }

    public void setEstimatedTime(double estimatedTime) {
        this.estimatedTime = estimatedTime;
    }

    public double getProgress() {
        return progress;
    }

    public void setProgress(double progress) {
        this.progress = progress;
    }

    public void addEmployee(Employee employee) {
        employees.add(employee);
    }

    public Set<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<Employee> employees) {
        this.employees = employees;
    }
}
