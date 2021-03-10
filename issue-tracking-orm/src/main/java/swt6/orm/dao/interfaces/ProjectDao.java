package swt6.orm.dao.interfaces;

import swt6.orm.domain.Project;

public interface ProjectDao {
    Project create(Project project);
    Project update(Project project);
    void delete(Project project);
    Project findById(int id);
}
