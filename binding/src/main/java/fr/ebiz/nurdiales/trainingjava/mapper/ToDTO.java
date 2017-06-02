package fr.ebiz.nurdiales.trainingjava.mapper;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import fr.ebiz.nurdiales.trainingjava.dto.CompanyDTO;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;

import java.util.List;
import java.util.stream.Collectors;

public class ToDTO {
    /**
     * Convert a Computer to ComputerDTO.
     * @param computer .
     * @return DTO.
     */
    public static ComputerDTO toDTO(Computer computer) {
        ComputerDTO computerDTO = new ComputerDTO(computer.getId(),
                computer.getName(),
                computer.getIntroduced(),
                computer.getDiscontinued(),
                computer.getCompanyId(),
                computer.getCompanyName());
        return computerDTO;
    }

    /**
     * Convert a Company to CompanyDTO.
     * @param company .
     * @return DTO.
     */
    public static CompanyDTO toDTO(Company company) {
        CompanyDTO companyDTO = new CompanyDTO(company.getId(),
                company.getName());
        return null;
    }

    /**
     * Convert a List of Computers to ComputerDTOs.
     * @param computers .
     * @return List of DTO.
     */
    public static List<ComputerDTO> computerListToDTOs(List<Computer> computers) {
        return computers.stream().map(e -> toDTO(e)).collect(Collectors.toList());
    }

    /**
     * Convert a List of Companies to CompanyDTOs.
     * @param companies .
     * @return List of DTO.
     */
    public static List<CompanyDTO> companyListToDTOs(List<Company> companies) {
        return companies.stream().map(e -> toDTO(e)).collect(Collectors.toList());
    }
}
