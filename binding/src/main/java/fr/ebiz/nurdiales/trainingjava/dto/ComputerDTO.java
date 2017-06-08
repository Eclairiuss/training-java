package fr.ebiz.nurdiales.trainingjava.dto;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;

import java.time.LocalDate;

public class ComputerDTO {
    private Integer id;
    private Integer companyId;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;

    /**
     * default constructor.
     */
    public ComputerDTO() { }

    /**
     * Full constructor.
     * @param id .
     * @param name .
     * @param introduced .
     * @param discontinued .
     * @param companyId .
     * @param companyName .
     */
    public ComputerDTO(Integer id, String name, LocalDate introduced, LocalDate discontinued, Integer companyId, String companyName) {
        this.id = id;
        this.name = name;
        this.introduced = introduced == null ? null : introduced.toString();
        this.discontinued = discontinued == null ? null : discontinued.toString();
        this.companyId = companyId;
        this.companyName = companyName;
    }

    /**
     * Constructor with dto content.
     * @param id .
     * @param name .
     * @param introduced .
     * @param discontinued .
     * @param companyId .
     * @param companyName .
     */
    public ComputerDTO(Integer id, String name, String introduced, String discontinued, Integer companyId, String companyName) {
        this.id = id;
        this.name = name;
        this.introduced = introduced;
        this.discontinued = discontinued;
        this.companyId = companyId;
        this.companyName = companyName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduced() {
        return introduced;
    }

    public void setIntroduced(String introduced) {
        this.introduced = introduced;
    }

    public String getDiscontinued() {
        return discontinued;
    }

    public void setDiscontinued(String discontinued) {
        this.discontinued = discontinued;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    /**
     * Create the listComputers corresponding to the dto.
     * @return Computer.
     */
    public Computer toComputer() {
        if (id != null) {
            id = -1;
        }
        Computer c = new Computer()
                .id(id)
                .name(name)
                .introduced(introduced)
                .discontinued(discontinued);
        return companyId != null ? c.company(new Company(companyId, companyName)) : c;
    }

    @Override
    public String toString() {
        return "[" +
                       "id='" + id + '\'' +
                       ", name='" + name + '\'' +
                       ", introduced='" + introduced + '\'' +
                       ", discontinued='" + discontinued + '\'' +
                       ", companyName='" + companyName + '\'' +
                       ']';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        ComputerDTO that = (ComputerDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        if (companyId != null ? !companyId.equals(that.companyId) : that.companyId != null) {
            return false;
        }
        if (name != null ? !name.equals(that.name) : that.name != null) {
            return false;
        }
        if (introduced != null ? !introduced.equals(that.introduced) : that.introduced != null) {
            return false;
        }
        if (discontinued != null ? !discontinued.equals(that.discontinued) : that.discontinued != null) {
            return false;
        }
        return companyName != null ? companyName.equals(that.companyName) : that.companyName == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (companyId != null ? companyId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (introduced != null ? introduced.hashCode() : 0);
        result = 31 * result + (discontinued != null ? discontinued.hashCode() : 0);
        result = 31 * result + (companyName != null ? companyName.hashCode() : 0);
        return result;
    }
}
