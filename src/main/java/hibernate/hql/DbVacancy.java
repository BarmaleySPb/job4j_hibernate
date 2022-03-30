package hibernate.hql;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "dbvacancies")
public class DbVacancy {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Vacancy> vacancies = new ArrayList<>();

    public DbVacancy() {

    }

    public static DbVacancy of(String name, List<Vacancy> vacancies) {
        DbVacancy dbVacancy = new DbVacancy();
        dbVacancy.name = name;
        dbVacancy.vacancies = vacancies;
        return dbVacancy;
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

    public List<Vacancy> getVacancies() {
        return vacancies;
    }

    public void setVacancies(List<Vacancy> vacancies) {
        this.vacancies = vacancies;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DbVacancy dbVacancy = (DbVacancy) o;
        return id == dbVacancy.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
