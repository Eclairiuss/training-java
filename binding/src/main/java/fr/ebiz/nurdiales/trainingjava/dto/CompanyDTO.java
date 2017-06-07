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

    @Override
    public String toString() {
        return "CompanyDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        CompanyDTO that = (CompanyDTO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) {
            return false;
        }
        return name != null ? name.equals(that.name) : that.name == null;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        return result;
    }
}
