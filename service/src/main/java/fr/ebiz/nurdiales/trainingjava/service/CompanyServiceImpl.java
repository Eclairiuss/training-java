package fr.ebiz.nurdiales.trainingjava.service;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.dto.CompanyDTO;
import fr.ebiz.nurdiales.trainingjava.mapper.ToDTO;
import fr.ebiz.nurdiales.trainingjava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingjava.persistence.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private List<Company> companies = null;
    private CompanyDAO companyDAO;
    private ComputerDAO computerDAO;

    /**
     * Default constructor.
     * @param computerDAO data access object for computers.
     * @param companyDAO  data access object for companies.
     */
    @Autowired
    public CompanyServiceImpl(ComputerDAO computerDAO, CompanyDAO companyDAO) {
        this.computerDAO = computerDAO;
        this.companyDAO = companyDAO;
    }

    public void setCompanyDAO(CompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void setComputerDAO(ComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }

    @Override
    public List<CompanyDTO> getAll() {
        if (companies == null) {
            companies = companyDAO.listCompanies();
        }
        return ToDTO.companyListToDTOs(companies);
    }

    @Override
    public List<CompanyDTO> getAll(int page, int pageSize) {
        return ToDTO.companyListToDTOs(companyDAO.listCompanies(page, pageSize));
    }

    @Override
    public List<CompanyDTO> getAll(String name) {
        return ToDTO.companyListToDTOs(companyDAO.listCompanies(name));
    }

    @Override
    public List<CompanyDTO> getAll(String name, int page, int pageSize) {
        return ToDTO.companyListToDTOs(companyDAO.listCompanies(name, page, pageSize));
    }

    @Override
    public CompanyDTO get(int id) {
        if (companies != null) {
            for (Company company : companies) {
                if (id == company.getId()) {
                    return ToDTO.toDTO(company);
                }
            }
        }
        return ToDTO.toDTO(companyDAO.getCompany(id));
    }

    @Override
    public CompanyDTO get(String sId) {
        try {
            int id = Integer.parseInt(sId);
            if (companies != null) {
                for (Company company : companies) {
                    if (id == company.getId()) {
                        return ToDTO.toDTO(company);
                    }
                }
            }
            return ToDTO.toDTO(companyDAO.getCompany(id));
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public int delete(int i) {
        computerDAO.deleteByCompany(i);
        int retour = companyDAO.delete(new Integer(i));
        companies = null;
        return retour;
    }

    @Override
    public int delete(String id) {
        try {
            return delete(Integer.parseInt(id));
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
