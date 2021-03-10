package swt6.orm.logic.interfaces;

import swt6.orm.domain.Issue;

public interface IssueLogic {
    Issue create(Issue issue);
    Issue update(Issue issue);
    void delete(Issue issue);
    Issue findById(int id);
}
