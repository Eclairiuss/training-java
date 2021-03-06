package fr.ebiz.nurdiales.trainingjava.core;

import fr.ebiz.nurdiales.trainingjava.core.util.Parse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import java.time.LocalDate;

@Entity(name = "computer")
public class Computer {
    private static final Logger LOGGER = LoggerFactory.getLogger(Computer.class);
    private static final LocalDate NEVER_BEFORE = Parse.stringToLocalDate("1970-01-01");

    @Id
    private int id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    @ManyToOne
    private Company company;

    /**
     * Constructor for listComputers, who takes all arguments.
     * @param id Id of the listComputers.
     * @param name Name of the listComputers.
     * @param introduced Date of introduced of Computer.
     * @param discontinued Date of discontinued of Computer.
     * @param company Company who have make the listComputers.
     */
    public Computer(int id, String name, LocalDate introduced, LocalDate discontinued, Company company) {
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
        this.name = (name == null) ? null : (!name.contains("%") && !name.contains("'")) ? name : this.name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    /**
     * Set Introduced.
     * @param introduced .
     */
    public void setIntroduced(LocalDate introduced) {
        this.introduced = (introduced != null) ? (introduced.isBefore(NEVER_BEFORE) ? this.introduced : introduced)
                                  : null;
    }
    /**
     * Set Introduced.
     * @param introduced Date with format "AAAA-MM-JJ"
     */
    public void setIntroduced(String introduced) {
        LocalDate intro = Parse.stringToLocalDate(introduced);
        setIntroduced(intro);
    }

    public LocalDate getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(LocalDate discontinued) {
        this.discontinued = (discontinued != null)
                                    ? (discontinued.isBefore(NEVER_BEFORE) ? this.discontinued : discontinued) : null;
    }

    /**
     * Setter for discontinued who parse a string.
     * @param discontinued Date with format "AAAA-MM-JJ"
     */
    public void setDiscontinued(String discontinued) {
        LocalDate discont = Parse.stringToLocalDate(discontinued);
        setDiscontinued(discont);
    }

    public Company getCompany() {
        return company;
    }

    public Integer getCompanyId() {
        return (company != null) ? company.getId() : null;
    }

    public String getCompanyName() {
        return (company != null) ? company.getName() : "";
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    /**
     * Set the name of the company.
     * @param name of the company
     */
    public void setCompanyName(String name) {
        if (company != null) {
            company.setName(name);
        }
    }

    /**
     * Set the name of the company.
     * @param id of the company.
     */
    public void setCompanyId(int id) {
        if (company == null) {
            company = new Company(0, "");
        }
        company.setId(id);
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                       + ", company=" + getCompanyName() + "]";
    }

    /**
     * Method who check if introduced is before discontinued.
     * @return true if it's ok (introduced is before discontinued) else false.
     */
    public boolean checkDates() {
        if (introduced == null || discontinued == null) {
            return true;
        }
        return introduced.isBefore(discontinued);
    }
}
