package fr.ebiz.nurdiales.trainingJava.controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import fr.ebiz.nurdiales.trainingJava.model.Computer;
import fr.ebiz.nurdiales.trainingJava.persistence.ComputerDAO;

/**
 * Servlet implementation class DashBoardComputer
 */
@WebServlet(name = "DashBoardComputer", urlPatterns = { "/DashBoardComputer" })
public class DashBoardComputer extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String DASHBOARDCOMPUTER_VIEW = "/WEB-INF/dashBoardComputer.jsp";

	/**
	 * Default constructor.
	 */
	public DashBoardComputer() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		try {
			String start = request.getParameter("start");
			String size = request.getParameter("size");
			if (start != null && size != null) {
				int a = Integer.parseInt(start);
				int b = Integer.parseInt(size);
				List<Computer> listComputers = ComputerDAO.requestAllComputers(a, b);
				request.setAttribute("computers", listComputers);
			}
		} catch (SQLException | IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		this.getServletContext().getRequestDispatcher(DASHBOARDCOMPUTER_VIEW).forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
