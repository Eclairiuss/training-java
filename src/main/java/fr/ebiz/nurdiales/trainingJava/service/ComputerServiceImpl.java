package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.exceptions.DAOCompanyException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOComputerException;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
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
        try {
            computerDAO.create(c.toComputer());
        } catch (DAOComputerException e) {
            LOGGER.warn("ComputerServiceImpl : error while creating computer");
            throw new RuntimeException("ComputerServiceImpl : Impossible to create computer");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(int id) {
        try {
            computerDAO.delete(id);
        } catch (DAOComputerException e) {
            LOGGER.warn("ComputerServiceImpl : error while deleting computer");
            throw new RuntimeException("ComputerServiceImpl : Impossible to delete computer");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void delete(Integer[] ids) {
        try {
            computerDAO.delete(ids);
        } catch (DAOComputerException e) {
            LOGGER.warn("ComputerServiceImpl : error while delete computer");
            throw new RuntimeException("ComputerServiceImpl : Impossible to delete computers");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public void update(ComputerDTO c) {
        try {
            computerDAO.update(c.toComputer());
        } catch (DAOComputerException e) {
            LOGGER.warn("ComputerServiceImpl : error while updating computer");
            throw new RuntimeException("ComputerServiceImpl : Impossible to update computer");
        }
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public ComputerDTO get(int id) {
        ComputerDTO retour = null;
        try {
            retour = computerDAO.getComputer(id);
        } catch (DAOComputerException e) {
            LOGGER.warn("ComputerServiceImpl : error while getting computer");
            throw new RuntimeException("ComputerServiceImpl : Impossible to get computer");
        }
        return retour;
    }

    @Override
    @Transactional(rollbackFor = {Exception.class})
    public Page getAll(Parameters params) {
        List<CompanyDTO> list = null;
        try {
            list = companyDAO.listCompanies();
        } catch (DAOCompanyException e) {
            LOGGER.warn("ComputerServiceImpl : error while getting companies");
            throw new RuntimeException("ComputerServiceImpl : Impossible to get companies");
        }
        Page page = null;
        try {
            page = computerDAO.listComputers(params, list);
        } catch (DAOComputerException e) {
            LOGGER.warn("ComputerServiceImpl : error while get computers");
            throw new RuntimeException("ComputerServiceImpl : Impossible to get computers");
        }
        for (CompanyDTO company : list) {
            for (ComputerDTO computer : page.getComputers()) {
                if (company.getId().equals(computer.getCompanyId())) {
                    computer.setCompanyName(company.getName());
                }
            }
        }
        return page;
    }
}
