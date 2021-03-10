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
    private String activity;
    private LocalDateTime startTime;
    private LocalDateTime endTime;

    // fetching strategies:
    //  [FetchMode.JOIN]    [FetchType.EAGER]   1 join, eager fetch
    //  FetchMode.SELECT    [FetchType.EAGER]   2 selects, eager fetch
    //  [FetchMode.SELECT]  FetchType.LAZY      2 selects, lazy fetch
    //  FetchMode.JOIN      FetchType.LAZY      contradictory
    // Default fetch strategy: EAGER JOIN
    @org.hibernate.annotations.Fetch(FetchMode.JOIN)
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE}, fetch = FetchType.EAGER)
    //@JoinColumn(name = "EMPLOYEE_ID") // default
    private Employee employee;

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

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        return activity + ":"
                + startTime.format(formatter) + " - "
                + endTime.format((formatter));
    }
}
