package swt6.orm.dao.interfaces;

import swt6.orm.domain.Issue;

public interface IssueDao {
    Issue create(Issue issue);
    Issue update(Issue issue);
    void delete(Issue issue);
    Issue findById(int id);
}
