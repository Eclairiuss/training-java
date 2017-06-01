package fr.ebiz.nurdiales.trainingjava.service;

import fr.ebiz.nurdiales.trainingjava.core.Company;
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
    public List<Company> getAll() {
        if (companies == null) {
            companies = companyDAO.listCompanies();
        }
        return companies;
    }

    @Override
    public List<Company> getAll(int page, int pageSize) {
        return companyDAO.listCompanies(page, pageSize);
    }

    @Override
    public List<Company> getAll(String name) {
        return companyDAO.listCompanies(name);
    }

    @Override
    public List<Company> getAll(String name, int page, int pageSize) {
        return companyDAO.listCompanies(name, page, pageSize);
    }

    @Override
    public Company get(int id) {
        return companyDAO.getCompany(id);
    }

    @Override
    public Company get(String sId) {
        try {
            int id = Integer.parseInt(sId);
            return companyDAO.getCompany(id);
        } catch (NumberFormatException e) {
            return null;
        }
    }

    @Override
    @Transactional
    public void delete(int i) {
        computerDAO.deleteByCompany(i);
        companyDAO.delete(new Integer(i));
        companies = null;
    }
}
