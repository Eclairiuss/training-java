package fr.ebiz.nurdiales.trainingJava.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class Computer {
    private static final LocalDate NEVER_BEFORE = stringToDate("1970-01-01");

    private int id;
    private String name;
    private LocalDate introduced;
    private LocalDate discontinued;
    private Company company;

    /**
     * Constructor for computer, who takes all arguments.
     * @param id Id of the computer.
     * @param name Name of the computer.
     * @param introduced Date of introduced of Computer.
     * @param discontinued Date of discontinued of Computer.
     * @param company Company who have make the computer.
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
        this.name = (!name.contains("%") && !name.contains("'")) ? name : this.name;
    }

    public LocalDate getIntroduced() {
        return introduced;
    }

    public void setIntroduced(LocalDate introduced) {
        this.introduced = (introduced != null) ? (introduced.isBefore(NEVER_BEFORE) ? this.introduced : introduced)
                                  : null;
    }
    /**
     * Setter for introduced who parse a string.
     * @param introduced Date with format "AAAA-MM-JJ"
     */
    public void setIntroduced(String introduced) {
        LocalDate intro = stringToDate(introduced);
        this.introduced = (intro != null)
                                    ? (intro.isBefore(NEVER_BEFORE) ? this.introduced : intro) : null;
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
        LocalDate discont = stringToDate(discontinued);
        this.discontinued = (discont != null)
                                    ? (discont.isBefore(NEVER_BEFORE) ? this.discontinued : discont) : null;
    }

    public Company getCompany() {
        return company;
    }

    public String getCompanyName() {
        return (company != null) ? company.getName() : "";
    }

    public void setCompany(Company company) {
        this.company = company;
    }

    @Override
    public String toString() {
        return "Computer [id=" + id + ", name=" + name + ", introduced=" + introduced + ", discontinued=" + discontinued
                       + ", company=" + getCompanyName() + "]";
    }

    /**
     * Function to transform string to a Date.
     * @param s Date in string.
     * @return Date from the param
     */
    private static LocalDate stringToDate(String s) {
        if (s == null || s.equals("")) {
            return null;
        }
        try {
            return LocalDate.parse(s.split(" ")[0], DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (DateTimeParseException e) {
            return null;
        }
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
