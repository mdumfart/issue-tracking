package swt6.orm.logic.factory;

import swt6.orm.dao.implementation.*;
import swt6.orm.logic.implementation.*;
import swt6.orm.logic.interfaces.*;

public class JpaIssueTrackingLogicFactory implements IssueTrackingLogicFactory{
    @Override
    public AddressLogic getAddressLogic() {
        return new AddressLogicImpl(new JpaAddressDao());
    }

    @Override
    public EmployeeLogic getEmployeeLogic() {
        return new EmployeeLogicImpl(new JpaEmployeeDao());
    }

    @Override
    public IssueLogic getIssueLogic() {
        return new IssueLogicImpl(new JpaIssueDao());
    }

    @Override
    public LogbookEntryLogic getLogbookEntryLogic() {
        return new LogbookEntryLogicImpl(new JpaLogbookEntryDao());
    }

    @Override
    public ProjectLogic getProjectLogic() {
        return new ProjectLogicImpl(new JpaProjectDao());
    }
}
