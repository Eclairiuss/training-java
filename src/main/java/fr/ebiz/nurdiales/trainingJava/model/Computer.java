package fr.ebiz.nurdiales.trainingJava.model;

import java.sql.Date;

public class Computer {
    private static final Date NEVER_BEFORE = Date.valueOf("1970-01-01");

    private int id;
    private String name;
    private Date introduced;
    private Date discontinued;
    private Company company;

    /**
     * Constructor for computer, who takes all arguments.
     * @param id Id of the computer.
     * @param name Name of the computer.
     * @param introduced Date of introduced of Computer.
     * @param discontinued Date of discontinued of Computer.
     * @param company Company who have make the computer.
     */
    public Computer(int id, String name, Date introduced, Date discontinued, Company company) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.company = company;
    }

    /**
     * Default Constructor for Computer.
     */
    public Computer() {

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

    public Date getIntroduced() {
        return introduced;
    }

    public void setIntroduced(Date introduced) {
        this.introduced = (introduced != null) ? (introduced.before(NEVER_BEFORE) ? this.introduced : introduced)
                : null;
    }

    public Date getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(Date discontinued) {
        this.discontinued = (discontinued != null)
                ? (discontinued.before(NEVER_BEFORE) ? this.discontinued : discontinued) : null;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                + ", company=" + company + "]";
    }

    /**
     * Method who check if introduced is before discontinued.
     * @return false is Introduce isn't before discontinued. else return true.
     */
    public boolean checkDates() {
        if (introduced == null || discontinued == null) {
            return true;
        }
        return introduced.before(discontinued);
    }
}
