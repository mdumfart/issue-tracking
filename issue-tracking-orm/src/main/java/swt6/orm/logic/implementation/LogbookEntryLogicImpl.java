package swt6.orm.logic.implementation;

import swt6.orm.dao.interfaces.LogbookEntryDao;
import swt6.orm.domain.LogbookEntry;
import swt6.orm.logic.interfaces.LogbookEntryLogic;
import swt6.util.JpaUtil;

public class LogbookEntryLogicImpl implements LogbookEntryLogic {
    private final LogbookEntryDao logbookEntryDao;

    public LogbookEntryLogicImpl(LogbookEntryDao logbookEntryDao) {
        this.logbookEntryDao = logbookEntryDao;
    }

    @Override
    public LogbookEntry create(LogbookEntry logbookEntry) {
        LogbookEntry createdLogbookEntry = null;

        try {
            JpaUtil.openTransaction();

            createdLogbookEntry = logbookEntryDao.create(logbookEntry);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return createdLogbookEntry;
    }

    @Override
    public LogbookEntry update(LogbookEntry logbookEntry) {
        LogbookEntry updatedLogbookEntry = null;

        try {
            JpaUtil.openTransaction();

            updatedLogbookEntry = logbookEntryDao.update(logbookEntry);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return updatedLogbookEntry;
    }

    @Override
    public void delete(LogbookEntry logbookEntry) {
        try {
            JpaUtil.openTransaction();

            logbookEntryDao.delete(logbookEntry);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }
    }

    @Override
    public LogbookEntry findById(int id) {
        LogbookEntry logbookEntry = null;

        try {
            JpaUtil.openTransaction();

            logbookEntry = logbookEntryDao.findById(id);

            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return logbookEntry;
    }
}
