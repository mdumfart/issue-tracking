package swt6.orm.domain;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashSet;
import java.util.Set;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "employee_type", discriminatorType = DiscriminatorType.STRING)
@DiscriminatorValue("E")
public class Employee implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String firstName;
    private String lastName;
    private LocalDate dateOfBirth;

    @OneToOne
    private Address address;


    // fetching strategies:
    //   [FetchMode.JOIN]    FetchType.EAGER   1 join, eager fetch
    //   FetchMode.SELECT    FetchType.EAGER   2 selects, eager fetch
    //   [FetchMode.SELECT]  [FetchType.LAZY]  2 selects, lazy fetch
    //   FetchMode.JOIN      [FetchType.LAZY]  contradictory
    // Default fetch strategy: LAZY
    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<LogbookEntry> logbookEntries = new HashSet<>();

    @ElementCollection
    @CollectionTable(name = "EMPL_PHONES", joinColumns = @JoinColumn(name = "EMPL_ID"))
    @Column(name = "PHONE_NUMBER")
    private Set<String> phones = new HashSet<>();
    
    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Issue> issues = new HashSet<>();

    // Classes persisted with Hibernate must have a default constructor
    // (newInstance of reflection API)
    public Employee() {
    }

    public Employee(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public Set<String> getPhones() {
        return phones;
    }

    public void setPhones(Set<String> phones) {
        this.phones = phones;
    }

    public void addPhones(String phone) {
        phones.add(phone);
    }

    public Set<LogbookEntry> getLogbookEntries() {
        return logbookEntries;
    }

    public void setLogbookEntries(Set<LogbookEntry> logbookEntries) {
        this.logbookEntries = logbookEntries;
    }

    public void addLogbookEntry(LogbookEntry entry) {
        if (entry.getEmployee() != null) {
            entry.getEmployee().logbookEntries.remove(entry);
        }

        logbookEntries.add(entry);
        entry.setEmployee(this);
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setEmployee(this);
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue);
        issue.setEmployee(null);
    }

    @Override
    public String toString() {
        DateTimeFormatter fmt = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%d: %s, %s (%s)", id, lastName, firstName, dateOfBirth.format(fmt)));
        if (address != null) {
            sb.append(", " + address);
        }
        return sb.toString();
    }
}
