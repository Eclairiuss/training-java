package fr.ebiz.nurdiales.trainingjava.service;

import fr.ebiz.nurdiales.trainingjava.core.Company;
import fr.ebiz.nurdiales.trainingjava.core.Computer;
import fr.ebiz.nurdiales.trainingjava.core.Page;
import fr.ebiz.nurdiales.trainingjava.core.Parameters;
import fr.ebiz.nurdiales.trainingjava.dto.ComputerDTO;
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
    public int add(ComputerDTO c) {
        return computerDAO.create(c.toComputer());
    }

    @Override
    @Transactional
    public int delete(int id) {
        return computerDAO.delete(id);
    }

    @Override
    @Transactional
    public int delete(int[] ids) {
        return computerDAO.delete(ids);
    }

    @Override
    @Transactional
    public int update(Computer c) {
        return computerDAO.update(c);
    }

    @Override
    public ComputerDTO get(int id) {
        List<Company> list = companyDAO.listCompanies();
        Computer computer = computerDAO.getComputer(id);
        for (Company company : list) {
            if (company != null && computer != null && computer.getCompany() != null && company.getId() == computer.getCompanyId()) {
                computer.setCompanyName(company.getName());
            }
        }
        return ToDTO.toDTO(computer);
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

    @Override
    public int update(ComputerDTO computerDTO) {
       return update(computerDTO.toComputer());
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
