package fr.ebiz.nurdiales.trainingJava.model;

public class Company {

    private int id;
    private String name;

    /**
     * Constructor of Company.
     * @param id id of the company
     * @param name name of the company
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
