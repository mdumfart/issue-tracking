package swt6.orm.domain;

import org.hibernate.annotations.FetchMode;
import swt6.orm.domain.util.IssuePriority;
import swt6.orm.domain.util.IssueState;

import javax.persistence.*;
import java.io.Serializable;

@Entity
public class Issue implements Serializable {
    @Id
    @GeneratedValue
    private Long id;

    @Enumerated(EnumType.STRING)
    private IssueState state;

    @Enumerated(EnumType.STRING)
    private IssuePriority priority;

    private double estimatedTime;
    private double progress;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Employee employee;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    private Project project;

    public Issue() {
    }

    public Issue(IssueState state, IssuePriority priority, double progress, Project project) {
        this.state = state;
        this.priority = priority;
        this.progress = progress;
        this.project = project;
    }

    public Issue(IssueState state, IssuePriority priority, double estimatedTime, double progress, Project project) {
        this(state, priority, progress, project);
        this.estimatedTime = estimatedTime;
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

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employees) {
        this.employee = employees;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
