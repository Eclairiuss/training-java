package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOComputerException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOException;
import fr.ebiz.nurdiales.trainingJava.model.Company;
import fr.ebiz.nurdiales.trainingJava.persistence.InterfaceCompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.InterfaceComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyServiceImpl implements CompanyService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CompanyServiceImpl.class);

    private InterfaceCompanyDAO companyDAO;
    private InterfaceComputerDAO computerDAO;

    /**
     * Default constructor.
     * @param computerDAO data access object for computers.
     * @param companyDAO  data access object for companies.
     */
    @Autowired
    public CompanyServiceImpl(InterfaceComputerDAO computerDAO, InterfaceCompanyDAO companyDAO) {
        this.computerDAO = computerDAO;
        this.companyDAO = companyDAO;
    }

    public void setCompanyDAO(InterfaceCompanyDAO companyDAO) {
        this.companyDAO = companyDAO;
    }

    public void setComputerDAO(InterfaceComputerDAO computerDAO) {
        this.computerDAO = computerDAO;
    }

    @Override
    public List<Company> getAll() {
        try {
            return companyDAO.listCompanies();
        } catch (DAOException e) {
            LOGGER.warn("CompanyServiceImpl : error while getting companies");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get companies");
        }
    }

    @Override
    public List<Company> getAll(int page, int pageSize) {
        try {
            return companyDAO.listCompanies(page, pageSize);
        } catch (DAOException e) {
            LOGGER.warn("CompanyServiceImpl : error while getting pagined companies");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get paged companies");
        }
    }

    @Override
    public List<Company> getAll(String name) {
        try {
            return companyDAO.listCompanies(name);
        } catch (DAOException e) {
            LOGGER.warn("CompanyServiceImpl : error while getting name_search companies");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get name_search companies");
        }
    }

    @Override
    public List<Company> getAll(String name, int page, int pageSize) {
        try {
            return companyDAO.listCompanies(name, page, pageSize);
        } catch (DAOException e) {
            LOGGER.warn("CompanyServiceImpl : error while getting name_search and paged companies");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get name_search and paged companies");
        }
    }

    @Override
    public Company get(int id) {
        try {
            return companyDAO.getCompany(id);
        } catch (DAOException e) {
            LOGGER.warn("CompanyServiceImpl : error while getting specific company");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get a specific company");
        }
    }

    @Override
    public Company get(String sId) {
        try {
            try {
                int id = Integer.parseInt(sId);
                return companyDAO.getCompany(id);
            } catch (NumberFormatException e) {
                return null;
            }
        } catch (DAOException e) {
            LOGGER.warn("CompanyServiceImpl : error while getting specific company");
            throw new RuntimeException("CompanyServiceImpl : Impossible to get a specific company");
        }
    }

    @Override
    public void delete(int i) {
        try {
        computerDAO.deleteByCompany(i);
        companyDAO.delete(new Integer(i));
        } catch (DAOComputerException e) {
            LOGGER.warn("CompanyServiceImpl : error while delete computer of a specific company");
            throw new RuntimeException("CompanyServiceImpl : Impossible to delete computer of a specific company");
        } catch (DAOCompanyException e) {
            LOGGER.warn("CompanyServiceImpl : error while delete specific company");
            throw new RuntimeException("CompanyServiceImpl : Impossible to delete a specific company");
        }
    }
}
