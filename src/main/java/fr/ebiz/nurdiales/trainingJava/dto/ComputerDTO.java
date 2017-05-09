package fr.ebiz.nurdiales.trainingJava.dto;

import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.model.Computer;

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
        if (computer != null) {
            this.id = "" + computer.getId();
            this.name = computer.getName();
            introduced = computer.getIntroduced() == null ? null : computer.getIntroduced().toString();
            discontinued = computer.getDiscontinued() == null ? null : computer.getDiscontinued().toString();
            companyId = computer.getCompany() == null ? null : "" + computer.getCompany().getId();
            companyName = computer.getCompany() == null ? null : computer.getCompany().getName();
        }
    }

    /**
     * default constructor.
     */
    public ComputerDTO() { }

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

    /**
     * Create the computer corresponding to the dto.
     * @return Computer.
     */
    public Computer toComputer() {
        Computer c = new Computer();
        try {
            c.setId(Integer.parseInt(id));
            c.setName(name);
            c.setIntroduced(introduced);
            c.setDiscontinued(discontinued);
            if (companyId != null) {
                c.setCompany(new Company(Integer.parseInt(companyId), companyName));
            }
        } catch (IllegalArgumentException e) {
            System.out.println(id);
            System.out.println(companyId);
            e.printStackTrace();
        }
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
