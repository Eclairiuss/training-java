package fr.ebiz.nurdiales.trainingJava.dto;

import fr.ebiz.nurdiales.trainingJava.Computer;

public class ComputerDTO {
    private String id;
    private String companyId;
    private String name;
    private String introduced;
    private String discontinued;
    private String companyName;

    /**
     * constructor to transform object to DTO.
     * @param computer computer to translate.
     */
    public ComputerDTO(Computer computer) {
        this.id = "" + computer.getId();
        this.name = computer.getName();
        introduced = computer.getIntroduced() == null ? null : computer.getIntroduced().toString();
        discontinued = computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString();
        companyId = computer.getCompany() == null ? null : "" + computer.getCompany().getId();
        companyName = computer.getCompany() == null ? null : computer.getCompany().getName();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
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
}
