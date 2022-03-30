package hibernate.hql;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "books")
public class SecondBook {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String publishingHouse;

    public static SecondBook of(String name, String publishingHouse) {
        SecondBook b = new SecondBook();
        b.name = name;
        b.publishingHouse = publishingHouse;
        return b;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPublishingHouse() {
        return publishingHouse;
    }

    public void setPublishingHouse(String publishingHouse) {
        this.publishingHouse = publishingHouse;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SecondBook secondBook = (SecondBook) o;
        return id == secondBook.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Book{"
                + "id=" + id
                + ", name='" + name + '\''
                + ", publishingHouse='" + publishingHouse + '\''
                + '}';
    }
}