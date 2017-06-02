package fr.ebiz.nurdiales.trainingjava.dto;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;

public class ComputerDTO {
    private Integer id;
    private Integer companyId;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;

    /**
     * constructor to transform object to DTO.
     * @param computer listComputers to translate.
     */
    public ComputerDTO(Computer computer) {
        if (computer != null) {
            this.id = computer.getId();
            this.name = computer.getName();
            introduced = computer.getIntroduced() == null ? null : computer.getIntroduced().toString();
            discontinued = computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString();
            companyId = computer.getCompany() == null ? null : computer.getCompany().getId();
            companyName = computer.getCompany() == null ? null : computer.getCompany().getName();
        }
    }

    /**
     * default constructor.
     */
    public ComputerDTO() { }

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
        Computer c = new Computer().id(id).name(name).introduced(introduced).discontinued(discontinued).company(new Company(companyId, companyName));
        return c;
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
}
