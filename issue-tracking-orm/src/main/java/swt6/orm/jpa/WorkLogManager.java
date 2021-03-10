//package swt6.orm.jpa;
//
//import swt6.orm.domain.*;
//import swt6.orm.domain.util.AddressPK;
//import swt6.util.JpaUtil;
//
//import javax.persistence.*;
//import javax.persistence.criteria.CriteriaBuilder;
//import javax.persistence.criteria.CriteriaQuery;
//import javax.persistence.criteria.ParameterExpression;
//import javax.persistence.criteria.Root;
//import java.io.Serializable;
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.util.List;
//import java.util.Optional;
//import java.util.Set;
//
//public class WorkLogManager {
//    private static void insertEmployee1 (Employee empl){
//        EntityManagerFactory emFactory = Persistence.createEntityManagerFactory("IssueTrackingPU");
//        EntityManager em = null;
//        EntityTransaction tx = null;
//
//        try{
//            em = emFactory.createEntityManager();
//            tx = em.getTransaction();
//            tx.begin();
//
//            em.persist(empl);
//
//            tx.commit();
//        } catch(Exception e){
//            if(tx != null && tx.isActive()){
//                tx.rollback();
//            }
//            throw e;
//        } finally{
//            if(em != null){
//                em.close();
//            }
//        }
//
//        emFactory.close();
//    }
//
//    private static void insertEmployee (Employee empl){
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//            em.persist(empl);
//            JpaUtil.commit();
//        } catch(Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//    }
//
//    private static void listEmployees(){
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//
//            List<Employee> emplList = em.createQuery("select e from Employee e", Employee.class).getResultList();
//            emplList.forEach( e -> {
//                System.out.println(e);
//
//                if (e.getPhones().size() > 0) {
//                    System.out.println("   phone numbers:");
//                    e.getPhones().forEach(phone -> System.out.println("     " + phone));
//                }
//
//                if(e.getLogbookEntries().size() > 0){
//                    System.out.println("   logbookEntries:");
//                    e.getLogbookEntries().forEach(lbe -> System.out.println("     " + lbe));
//                }
//            });
//
//            JpaUtil.commit();
//        } catch (Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//    }
//
//    private static Employee addLogbookEntries(Employee empl, LogbookEntry... entries){
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//
//            empl = em.merge(empl);
//            for(LogbookEntry entry : entries){
//                empl.addLogbookEntry(entry);
//            }
//
//            JpaUtil.commit();
//        } catch (Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//
//        return empl;
//    }
//
//    private static Employee addPhones(Employee empl, String... phones) {
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//
//            empl = em.merge(empl);
//            for(String phone : phones){
//                empl.addPhones(phone);
//            }
//
//            JpaUtil.commit();
//        } catch (Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//
//        return empl;
//    }
//
//    private static void listEmployeesResidingIn(String zipCode) {
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//
//            // Navigate through a relationship using queries
//            TypedQuery<Employee> qry = em.createQuery(
//                    "select e from Employee e where e.address.zipCode = :zipCode",
//                    Employee.class);
//
//            qry.setParameter("zipCode", zipCode);
//            qry.getResultList().forEach(System.out::println);
//
//            JpaUtil.commit();
//        } catch (Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//    }
//
//    private static void loadEmployeesWithEntries() {
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//
//            // Fetch join: Logbook entries are fetched together with employee
//            // even if lazy loading is defined in the mapping
//            TypedQuery<Employee> qry = em.createQuery(
//                    "select distinct e from Employee e join fetch e.logbookEntries",
//                    Employee.class);
//
//            qry.getResultList().forEach(System.out::println);
//
//            JpaUtil.commit();
//        } catch (Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//    }
//
//    private static void listEntriesOfEmployeeCQ(Employee empl) {
//        try{
//            EntityManager em = JpaUtil.getTransactedEntityManager();
//            CriteriaBuilder cb = em.getCriteriaBuilder();
//
//            CriteriaQuery<LogbookEntry> entryCQ = cb.createQuery(LogbookEntry.class);
//            Root<LogbookEntry> entry = entryCQ.from(LogbookEntry.class);
//            ParameterExpression<Employee> p = cb.parameter(Employee.class);
//
//            //entryCQ.where(cb.equal(entry.get("employee"), p)).select(entry);
//            entryCQ.where(cb.equal(entry.get(LogbookEntry_.employee), p)).select(entry);
//
//            TypedQuery<LogbookEntry> entriesOfEmplQry = em.createQuery(entryCQ);
//            entriesOfEmplQry.setParameter(p, empl);
//
//            entriesOfEmplQry.getResultList().forEach(System.out::println);
//
//            JpaUtil.commit();
//        } catch (Exception e){
//            JpaUtil.rollback();
//            throw e;
//        }
//    }
//
//    public static void main(String[] args) {
//        try{
//            System.out.println("-------- create schema -----------");
//            JpaUtil.getEntityManagerFactory();
//
//            PermanentEmployee pe = new PermanentEmployee("Bill", "Gates", LocalDate.of(1970, 1, 21));
//
//            Address addr1 = new Address("77777", "Redmon", "Main Street");
//
//            pe.setAddress(addr1);
//            pe.setSalary(5000.0);
//            Employee empl1 = pe;
//
//            TemporaryEmployee te = new TemporaryEmployee("Susanne", "Huber", LocalDate.of(1970, 1, 21));
//            te.setHourlyRate(50.0);
//            te.setRenter("Microsoft");
//            te.setStartDate(LocalDate.of(2021, 1, 1));
//            te.setEndDate(LocalDate.of(2021, 3, 1));
//            Employee empl2 = te;
//
//            LogbookEntry entry1 = new LogbookEntry("Analyse",
//                    LocalDateTime.of(2021, 01, 01, 8, 15),
//                    LocalDateTime.of(2021, 01, 01, 10,30));
//
//            LogbookEntry entry2 = new LogbookEntry("Implementierung",
//                    LocalDateTime.of(2021, 01, 01, 8, 15),
//                    LocalDateTime.of(2021, 01, 01, 10,30));
//
//            LogbookEntry entry3 = new LogbookEntry("Testen",
//                    LocalDateTime.of(2021, 01, 01, 8, 15),
//                    LocalDateTime.of(2021, 01, 01, 10,30));
//
//            System.out.println("-------- insertAddress ----------");
//            saveEntity(addr1);
//
//            System.out.println("-------- insertEmployee ----------");
////            insertEmployee(empl1);
////            insertEmployee(empl2);
//            empl1 = saveEntity(empl1);
//            empl2 = saveEntity(empl2);
//
//            System.out.println("-------- listEmployees -----------");
//            listEmployees();
//
//            System.out.println("-------- updateEntity ------------");
//            empl2.setLastName("Huber-Mayr");
//            empl2 = updateEntity(empl2.getId(), empl2, Employee.class);
//
//            System.out.println("-------- listEmployees -----------");
//            listEmployees();
//
//            System.out.println("-------- addLogbookEntries -------");
//            empl1 = addLogbookEntries(empl1, entry1);
//            empl2 = addLogbookEntries(empl2, entry2, entry3);
//
//            System.out.println("-------- listEmployees -----------");
//            listEmployees();
//
//            System.out.println("-------- addPhones -------");
//            empl1 = addPhones(empl1, "+436604280058", "+4372138693");
//
//            System.out.println("-------- listEmployees -----------");
//            listEmployees();
//
//            System.out.println("-------- testListEmployeesResidingIn -----------");
//            listEmployeesResidingIn("77777");
//
//            System.out.println("-------- loadEmployeesWithEntries -----------");
//            loadEmployeesWithEntries();
//
//            System.out.println("-------- listEntriesOfEmployee: Criteria query -----------");
//            listEntriesOfEmployeeCQ(empl1);
//        } finally {
//            JpaUtil.closeEntityManagerFactory();
//        }
//    }
//}
