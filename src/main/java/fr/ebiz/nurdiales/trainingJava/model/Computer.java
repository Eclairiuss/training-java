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

    public Date getDateOfIntroduced() {
        return introduced;
    }

    public void setDateOfIntroduced(Date introduced) {
        this.introduced = (introduced != null) ? (introduced.before(NEVER_BEFORE) ? this.introduced : introduced)
                : null;
    }

    public Date getDateOfDiscontinued() {
        return discontinued;
    }

    public void setDateOfDiscontinued(Date discontinued) {
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
<<<<<<< HEAD:src/main/java/fr/ebiz/nurdiales/trainingJava/model/Computer.java
     * Method who check if introduced is before discontinued.
     * @return true if it's ok (introduced is before discontinued) else false.
=======
     * Check the validity of dates of computers.
     * @return false is Introduce isn't before discontinued. else return true.
>>>>>>> c9b01a48d449a4286b2074292ae686b75bc0a9a4:src/fr/ebiz/nurdiales/trainingJava/model/Computer.java
     */
    public boolean checkDates() {
        return !(introduced != null && discontinued != null && introduced.after(discontinued));
    }
}
