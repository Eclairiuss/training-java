package fr.ebiz.nurdiales.trainingjava.dto;

import fr.ebiz.nurdiales.trainingjava.core.Company;

public class CompanyDTO {
    private Integer id;
    private String name;

    /**
     * Constructor.
     */
    public CompanyDTO() {
    }

    /**
     * Full constructor.
     * @param id .
     * @param name .
     */
    public CompanyDTO(int id, String name) {
        this.id = id;
        this.name = name;
    }

    /**
     * Convert the DTO to Company.
     * @return the corresponding Company.
     */
    public Company toCompany() {
        return new Company(id, name);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
