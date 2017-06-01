package fr.ebiz.nurdiales.trainingjava.service;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingjava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingjava.persistence.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class ComputerServiceImpl implements ComputerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ComputerService.class);

    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Constructor of ComputerManager, create the computerDAO.
     */
    public ComputerServiceImpl() {

    }

    @Override
    @Transactional
    public void add(ComputerDTO c) {
        computerDAO.create(c.toComputer());
    }

    @Override
    @Transactional
    public void delete(int id) {
        computerDAO.delete(id);
    }

    @Override
    @Transactional
    public void delete(int[] ids) {
        computerDAO.delete(ids);
    }

    @Override
    @Transactional
    public void update(Computer c) {
        computerDAO.update(c);
    }

    @Override
    public Computer get(int id) {
        List<Company> list = companyDAO.listCompanies();
        Computer computer = computerDAO.getComputer(id);
        for (Company company : list) {
            if (company != null && computer != null && computer.getCompany() != null && company.getId() == computer.getCompanyId()) {
                computer.setCompanyName(company.getName());
            }
        }
        return computer;
    }

    @Override
    public Page getAll(Parameters params) {
        List<Company> list = companyDAO.listCompanies();
        Page page = computerDAO.listComputers(params, list);
        for (Company company : list) {
            for (Computer computer : page.getComputers()) {
                if (company != null && computer != null && computer.getCompany() != null && company.getId() == computer.getCompanyId()) {
                    computer.setCompanyName(company.getName());
                }
            }
        }
        return page;
    }
}
