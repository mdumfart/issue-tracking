package swt6.orm.dao.implementation;

import swt6.orm.dao.interfaces.LogbookEntryDao;
import swt6.orm.domain.LogbookEntry;
import swt6.util.JpaUtil;

import javax.persistence.EntityManager;

public class JpaLogbookEntryDao implements LogbookEntryDao {
    @Override
    public LogbookEntry create(LogbookEntry logbookEntry) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.merge(logbookEntry);
    }

    @Override
    public LogbookEntry update(LogbookEntry logbookEntry) {
        return JpaUtil.updateEntity(logbookEntry.getId(), logbookEntry, LogbookEntry.class);
    }

    @Override
    public void delete(LogbookEntry logbookEntry) {
        EntityManager em = JpaUtil.getEntityManager();

        em.remove(logbookEntry);
    }

    @Override
    public LogbookEntry findById(long id) {
        EntityManager em = JpaUtil.getEntityManager();

        return em.find(LogbookEntry.class, id);
    }
}
