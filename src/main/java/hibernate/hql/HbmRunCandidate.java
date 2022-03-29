package hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRunCandidate {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /*
            Candidate firstCandidate = Candidate.of("Alex", 10, 2000);
            Candidate secondCandidate = Candidate.of("Petr", 20, 3000);
            Candidate thirdCandidate = Candidate.of("Ivan", 30, 4000);
            session.save(firstCandidate);
            session.save(secondCandidate);
            session.save(thirdCandidate);
            */

            Query query = session.createQuery("from Candidate");
            for (Object st : query.list()) {
                System.out.println(st);
            }

            Query queryForId = session.createQuery("from Candidate c where c.id = :key");
            queryForId.setParameter("key", 1);
            System.out.println(queryForId.uniqueResult());

            Query queryForName = session.createQuery("from Candidate c where c.name = :key");
            queryForName.setParameter("key", "Petr");
            System.out.println(queryForName.uniqueResult());

            session.createQuery("update Candidate c set c.experience = :newExperience, " +
                            "c.salary = :newSalary, c.name = :newName where c.id = :key")
                    .setParameter("newExperience", 50)
                    .setParameter("newSalary", 5000)
                    .setParameter("newName", "Vasiliy")
                    .setParameter("key", 1)
                    .executeUpdate();

            session.createQuery("delete from Candidate where id = :key")
                    .setParameter("key", 1)
                    .executeUpdate();

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}