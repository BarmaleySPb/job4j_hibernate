package hibernate.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "accounts")
public class Account {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String username;
    private boolean active;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SecondBook> secondBooks = new ArrayList<>();

    public void addBook(SecondBook secondBook) {
        this.secondBooks.add(secondBook);
    }

    public static Account of(String username) {
        Account a = new Account();
        a.username = username;
        a.active = true;
        return a;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public List<SecondBook> getBooks() {
        return secondBooks;
    }

    public void setBooks(List<SecondBook> secondBooks) {
        this.secondBooks = secondBooks;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Account account = (Account) o;
        return id == account.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Account{"
                + "id=" + id
                + ", username='" + username + '\''
                + ", active=" + active
                + ", books=" + secondBooks
                + '}';
    }
}