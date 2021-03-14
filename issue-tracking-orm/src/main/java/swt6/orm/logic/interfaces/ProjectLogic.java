package swt6.orm.logic.interfaces;

import swt6.orm.domain.Project;


public interface ProjectLogic {
    Project create(Project project);
    Project update(Project project);
    void delete(Project project);
    Project findById(int id);
}
