package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import hibernate.models.Car;
import hibernate.models.Model;

public class HbmRun {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Model first = Model.of("First model");
            Model second = Model.of("Second model");
            Model third = Model.of("Third model");
            Model fourth = Model.of("Fourth model");
            Model fifth = Model.of("Fifth model");
            session.save(first);
            session.save(second);
            session.save(third);
            session.save(fourth);
            session.save(fifth);

            Car car = Car.of("Car");
            car.addModel(session.load(Model.class, 1));
            car.addModel(session.load(Model.class, 2));
            car.addModel(session.load(Model.class, 3));
            car.addModel(session.load(Model.class, 4));
            car.addModel(session.load(Model.class, 5));

            session.save(car);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}