package fr.ebiz.nurdiales.trainingJava.service;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.DAOException;
import fr.ebiz.nurdiales.trainingJava.model.CompanyDTO;
import fr.ebiz.nurdiales.trainingJava.model.ComputerDTO;
import fr.ebiz.nurdiales.trainingJava.model.Page;
import fr.ebiz.nurdiales.trainingJava.model.Parameters;
import fr.ebiz.nurdiales.trainingJava.persistence.CompanyDAO;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ComputerManager {
    // private static Logger logger =
    // LoggerFactory.getLogger(ComputerManager.class);
    @Autowired
    private ComputerDAO computerDAO;
    @Autowired
    private CompanyDAO companyDAO;

    /**
     * Constructor of ComputerManager, create the computerDAO.
     */
    public ComputerManager() {

    }

    /**
     * Method to create a new listComputers in the database.
     * @param c Computer to create in the database, id don't need because the
     *            database generate it.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public void add(ComputerDTO c) throws ComputerDAOException {
        computerDAO.create(c.toComputer());
    }

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param id Id of the listComputers to delete.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public void delete(int id) throws ComputerDAOException {
        computerDAO.delete(id);
    }

    /**
     * Methode to delete a listComputers in the database by his id.
     * @param ids List of Ids of computers to delete.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public void delete(Integer[] ids) throws ComputerDAOException {
        computerDAO.delete(ids);
    }

    /**
     * Method to update a listComputers in the database.
     * @param c Computer to update in the database, the id of c must be in DB.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public void update(ComputerDTO c) throws ComputerDAOException {
        computerDAO.update(c.toComputer());
    }

    /**
     * Method to find a listComputers in the database by his id.
     * @param id of the researched listComputers.
     * @return ComputerDTO corresponding to the id.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public ComputerDTO get(int id) throws ComputerDAOException {
        ComputerDTO retour = computerDAO.getComputer(id);
        return retour;
    }

    /**
     * Général method for get all computers corresponding to parameters.
     * @param params contains all search arguments.
     * @return list of corresponding Computer.
     * @throws ComputerDAOException Error in the ComputerDAO SQL.
     * @throws CompanyDAOException Error in the CompanyDAO SQL.
     */
    @Transactional(rollbackFor = {DAOException.class})
    public Page getAll(Parameters params) throws ComputerDAOException, CompanyDAOException {
        List<CompanyDTO> list = companyDAO.listCompanies();
        Page page = computerDAO.listComputers(params, list);
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
