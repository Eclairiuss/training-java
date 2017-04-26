package fr.ebiz.nurdiales.trainingJava;

public class Company {

    private int id;
    private String name;

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
}
