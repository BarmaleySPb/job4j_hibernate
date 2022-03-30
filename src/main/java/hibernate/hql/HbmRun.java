package hibernate.hql;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.query.Query;

public class HbmRun {
    public static void main(String[] args) {
        Student rsl = null;

        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /*
            Student one = Student.of("Alex", 21, "Moscow");
            Student two = Student.of("Nikolay", 28, "Saint-Petersburg");
            Student three = Student.of("Nikita", 25, "Kaliningrad");

            session.save(one);
            session.save(two);
            session.save(three);
*/

            /*
            *
            * SELECT
            *
            Query query = session.createQuery("from Student");
            for (Object st : query.list()) {
                System.out.println(st);
            }
*/

            /*
            *
            * SELECT
            *
            Query query = session.createQuery("from Student s where s.id = 1");
            System.out.println(query.uniqueResult());
*/

            /*
            *
            * SELECT
            *
            Query query = session.createQuery("from Student s where s.id = :fId");
            query.setParameter("fId", 1);
            System.out.println(query.uniqueResult());
*/

            /*
            *
            * UPDATE
            *
            Query query = session.createQuery(
                    "update Student s set s.age = :newAge, s.city = :newCity where s.id = :fId"
            );
            query.setParameter("newAge", 22);
            query.setParameter("newCity", "London");
            query.setParameter("fId", 1);
            query.executeUpdate();
            */

            /*
            *
            * UPDATE
            *
            session.createQuery("update Student s set s.age = :newAge, s.city = :newCity where s.id = :fId")
                    .setParameter("newAge", 45)
                    .setParameter("newCity", "Moscow")
                    .setParameter("fId", 1)
                    .executeUpdate();
*/

            /*
            *
            * DELETE
            *
            session.createQuery("delete from Student where id = :fId")
                    .setParameter("fId", 3)
                    .executeUpdate();
*/

            /*
            *
            * INSERT
            *
            session.createQuery("insert into Student (name, age, city) "
                            + "select concat(s.name, 'NEW'), s.age + 5, s.city  "
                            + "from Student s where s.id = :fId")
                    .setParameter("fId", 1)
                    .executeUpdate();
*/

            rsl = session.createQuery(
                    "select distinct st from Student st "
                            + "join fetch st.account a "
                            + "join fetch a.secondBooks b "
                            + "where st.id = :sId", Student.class
            ).setParameter("sId", 1).uniqueResult();
            System.out.println(rsl);


            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}