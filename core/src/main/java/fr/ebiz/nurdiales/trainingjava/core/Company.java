package fr.ebiz.nurdiales.trainingjava.core;


import javax.persistence.Entity;
import javax.persistence.Id;

@Entity(name = "company")
public class Company {
    @Id
    private int id;
    private String name;

    /**
     * Constructor.
     */
    public Company() { }

    /**
     * Constructor with all arguments.
     * @param id Id of the company.
     * @param name Name of the company.
     */
    public Company(int id, String name) {
        this.id = id;
        this.name = name;
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

    @Override
    public String toString() {
        return "Company [id=" + id + ", name=" + name + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Company company = (Company) o;

        if (id != company.id) {
            return false;
        }
        return name != null ? name.equals(company.name) : company.name == null;
    }

    @Override
    public int hashCode() {
        int result = id;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
