package fr.ebiz.nurdiales.trainingJava.service;

import java.sql.SQLException;
import java.util.List;

import fr.ebiz.nurdiales.trainingJava.exceptions.CompanyDAOException;
import fr.ebiz.nurdiales.trainingJava.exceptions.ComputerDAOException;
import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

public class ComputerManager {
	private ComputerDAO computerDAO;

	public ComputerManager() {
		this.computerDAO = new ComputerDAO();
	}

	public List<Computer> requestAllComputers(int page, int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.requestAllComputers(page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public List<Computer> requestAllComputersByCompanyName(String name, int page, int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.requestAllComputersByCompanyName(name, page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public List<Computer> requestAllComputersByCompanyID(int idCompany, int page, int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.requestAllComputersByCompanyID(idCompany, page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public List<Computer> requestAllComputersByName(String name, int page, int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.requestAllComputersByName(name, page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public List<Computer> requestAllComputersByCompanyIDAndName(int idCompany, String name, int page, int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.requestAllComputersByCompanyIDAndName(idCompany, name, page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public List<Computer> requestAllComputersByCompanyNameAndName(String companyName, String name, int page,
			int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.requestAllComputersByCompanyNameAndName(companyName, name, page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public List<Computer> saladeTomateOignon(String companyName, int companyId, String name, int page, int pageSize) throws ComputerDAOException {
		List<Computer> computers = null;
		try {
			computers = computerDAO.saladeTomateOignon(companyName, companyId, name, page, pageSize);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computers;
	}

	public int add(Computer c) throws ComputerDAOException {
		int result = 0;
		try {
			result = computerDAO.add(c);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return result;
	}

	public int delete(int id) throws ComputerDAOException {
		int result = 0;
		try {
			result = computerDAO.delete(id);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return result;
	}

	public int update(Computer c) throws ComputerDAOException {
		int result = 0;
		try {
			result = computerDAO.update(c);
		} catch (SQLException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return result;
	}

	public Computer getComputerById(int id) throws ComputerDAOException {
		Computer computer = null;
		try {
			computer = computerDAO.getComputerById(id);
		} catch (SQLException | CompanyDAOException e) {
			e.printStackTrace();
			throw new ComputerDAOException();
		}
		return computer;
	}
}
