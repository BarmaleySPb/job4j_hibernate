package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import hibernate.models.Author;
import hibernate.models.Book;

public class HbmRunAB {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book firstBook = Book.of("first book");
            Book secondBook = Book.of("second book");
            Book thirdBook = Book.of("third book");

            Author firstAuthor = Author.of("Ivanov");
            Author secondAuthor = Author.of("Petrov");
            Author thirdAuthor = Author.of("Sidorov");

            firstAuthor.getBooks().add(firstBook);
            firstAuthor.getBooks().add(secondBook);
            secondAuthor.getBooks().add(secondBook);
            thirdAuthor.getBooks().add(thirdBook);

            session.persist(firstAuthor);
            session.persist(secondAuthor);
            session.persist(thirdAuthor);

            Author author = session.get(Author.class, 1);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        }  catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}