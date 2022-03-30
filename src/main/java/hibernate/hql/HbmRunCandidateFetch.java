package hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

import java.util.List;

public class HbmRunCandidateFetch {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Candidate firstCandidate = Candidate.of("First Candidate", 10, 1000);
            Candidate secondCandidate = Candidate.of("Second Candidate", 20, 2000);
            Candidate thirdCandidate = Candidate.of("Third Candidate", 30, 3000);
            session.save(firstCandidate);
            session.save(secondCandidate);
            session.save(thirdCandidate);

            Vacancy firstVacancy = Vacancy.of("first vacancy");
            Vacancy secondVacancy = Vacancy.of("second vacancy");
            Vacancy thirdVacancy = Vacancy.of("third vacancy");
            session.save(firstVacancy);
            session.save(secondVacancy);
            session.save(thirdVacancy);

            DbVacancy dbVacancy = new DbVacancy();
            dbVacancy.setVacancies(List.of(firstVacancy, secondVacancy, thirdVacancy));
            session.save(dbVacancy);
            secondCandidate.setDbVacancy(dbVacancy);

            Candidate result = session.createQuery(
                    "select distinct cand from Candidate cand "
                            + "join fetch cand.dbVacancy dbv "
                            + "join fetch dbv.vacancies vacs "
                            + "where cand.id = :sId", Candidate.class
            ).setParameter("sId", 2).uniqueResult();
            System.out.println(result);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}