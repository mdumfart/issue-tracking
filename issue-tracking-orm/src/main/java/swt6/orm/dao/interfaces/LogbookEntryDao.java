package swt6.orm.dao.interfaces;

import swt6.orm.domain.LogbookEntry;

public interface LogbookEntryDao {
    LogbookEntry create(LogbookEntry logbookEntry);
    LogbookEntry update(LogbookEntry logbookEntry);
    void delete(LogbookEntry logbookEntry);
    LogbookEntry findById(long id);
}
