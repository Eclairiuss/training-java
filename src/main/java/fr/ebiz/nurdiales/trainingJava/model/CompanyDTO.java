package fr.ebiz.nurdiales.trainingJava.model;

public class CompanyDTO {
    private Integer id;
    private String name;

    /**
     * Constructor.
     */
    public CompanyDTO() { }

    /**
     * constructor to transform object to DTO.
     * @param company company to translate.
     */
    public CompanyDTO(Company company) {
        this.id = company.getId();
        this.name = company.getName();
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
