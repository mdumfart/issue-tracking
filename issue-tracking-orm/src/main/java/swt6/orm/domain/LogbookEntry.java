package swt6.orm.domain;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Entity
public class LogbookEntry implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    @Column(nullable = false)
    private String activity;
    @Column(nullable = false)
    private LocalDateTime startTime;
    @Column(nullable = false)
    private LocalDateTime endTime;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Employee employee;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.LAZY)
    private Issue issue;

    public LogbookEntry() {
    }

    public LogbookEntry(String activity, LocalDateTime startTime, LocalDateTime endTime) {
        this.activity = activity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getActivity() {
        return activity;
    }

    public void setActivity(String activity) {
        this.activity = activity;
    }

    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public void attachEmployee(Employee employee) {
        if (this.employee != null) {
            this.employee.getLogbookEntries().remove(this);
        }

        if (employee != null) {
            employee.getLogbookEntries().add(this);
        }

        this.employee = employee;
    }

    public Issue getIssue() {
        return issue;
    }

    public void setIssue(Issue issue) {
        this.issue = issue;
    }

    public void attachIssue(Issue issue) {
        if (this.issue != null) {
            this.issue.getLogbookEntries().remove(this);
        }

        if (issue != null) {
            issue.getLogbookEntries().add(this);
        }

        this.issue = issue;
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return activity + ":"
                + startTime.format(formatter) + " - "
                + endTime.format((formatter));
    }
}
