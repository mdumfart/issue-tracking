package swt6.orm.logic.factory;

import swt6.orm.logic.interfaces.*;

public interface IssueTrackingLogicFactory {
    AddressLogic getAddressLogic();
    EmployeeLogic getEmployeeLogic();
    IssueLogic getIssueLogic();
    LogbookEntryLogic getLogbookEntryLogic();
    ProjectLogic getProjectLogic();
}
