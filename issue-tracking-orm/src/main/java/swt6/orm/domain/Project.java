package swt6.orm.domain;

import org.hibernate.annotations.FetchMode;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Project implements Serializable {
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;
    private String name;

    @org.hibernate.annotations.Fetch(FetchMode.SELECT)
    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Issue> issues = new HashSet<>();

    public Long getId() {
        return id;
    }

    public Project() {
    }

    public Project(String name) {
        this.name = name;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Issue> getIssues() {
        return issues;
    }

    public void setIssues(Set<Issue> issues) {
        this.issues = issues;
    }

    public void addIssue(Issue issue) {
        issues.add(issue);
        issue.setProject(this);
    }

    public void removeIssue(Issue issue) {
        issues.remove(issue);
        issue.setProject(null);
    }

    public String toString() {
        return String.format("%d: %s", id, name);
    }
}
