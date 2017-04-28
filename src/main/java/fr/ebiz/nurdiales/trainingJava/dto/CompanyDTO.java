package fr.ebiz.nurdiales.trainingJava.dto;

import fr.ebiz.nurdiales.trainingJava.model.Company;

public class CompanyDTO {
    private Integer id;
    private String name;

    /**
     * constructor to transform object to DTO.
     * @param company company to translate.
     */
    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
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
