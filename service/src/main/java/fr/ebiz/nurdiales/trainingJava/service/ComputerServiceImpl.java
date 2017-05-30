package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.binding.dto.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.core.Company;
import fr.ebiz.nurdiales.trainingJava.core.Computer;
import fr.ebiz.nurdiales.trainingJava.core.Page;
import fr.ebiz.nurdiales.trainingJava.core.Parameters;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    @Transactional(rollbackFor = {Exception.class})
    public void add(ComputerDTO c) {
        computerDAO.create(c.toComputer());
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(int id) {
        computerDAO.delete(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(int[] ids) {
        computerDAO.delete(ids);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(Computer c) {
        computerDAO.update(c);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Computer get(int id) {
        return computerDAO.getComputer(id);
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
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
