package swt6.orm.logic.interfaces;

import swt6.orm.domain.LogbookEntry;

public interface LogbookEntryLogic {
    LogbookEntry create(LogbookEntry logbookEntry);
    LogbookEntry update(LogbookEntry logbookEntry);
    void delete(LogbookEntry logbookEntry);
    LogbookEntry findById(int id);
}
