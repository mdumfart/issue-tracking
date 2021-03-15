package swt6.util;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;

public class JpaUtil {
    private static EntityManagerFactory emFactory;
    private static ThreadLocal<EntityManager> emThread = new ThreadLocal<>();

    public static synchronized EntityManagerFactory getEntityManagerFactory() {
        if (emFactory == null) {
            emFactory = Persistence.createEntityManagerFactory("IssueTrackingPUTest");
        }
        return emFactory;
    }

    public static synchronized EntityManager getEntityManager() throws IllegalStateException {
        if (emThread.get() == null) {
            emThread.set(getEntityManagerFactory().createEntityManager());
        }

        return emThread.get();
    }

    public static synchronized void closeEntityManager() {
        if (emThread.get() != null) {
            emThread.get().close();
            emThread.set(null);
        }
    }

    public static synchronized EntityManager getTransactedEntityManager() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (!tx.isActive()) tx.begin();

        return em;
    }

    public static synchronized void openTransaction() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (!tx.isActive()) {
            System.out.println("************ Open transaction ************");
            tx.begin();
        }
    }

    public static synchronized void commit() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive()) {
            System.out.println("************ Close transaction ***********");
            tx.commit();
        }
        closeEntityManager();
    }

    public static synchronized void rollback() {
        EntityManager em = getEntityManager();
        EntityTransaction tx = em.getTransaction();

        if (tx.isActive()) tx.rollback();
        closeEntityManager();
    }

    public static synchronized void closeEntityManagerFactory() {
        if (emFactory != null) {
            emFactory.close();
            emFactory = null;
        }
    }

    public static <T> T updateEntity(Serializable id, T entity, Class<T> type) {
        T storedEntity = null;

        EntityManager em = JpaUtil.getEntityManager();

        if (id == null) {
            throw new RuntimeException("Entity does not exist.");
        }

        storedEntity = em.find(type, id);
        if (storedEntity == null) {
            throw new RuntimeException("Entity does not exist.");
        }

        storedEntity = em.merge(entity);


        return storedEntity;
    }

    public static <T> T removeEntity(T entity) {
        try {
            EntityManager em = JpaUtil.getTransactedEntityManager();
            em.remove(entity);
            JpaUtil.commit();
        } catch (Exception e) {
            JpaUtil.rollback();
            throw e;
        }

        return entity;
    }
}

