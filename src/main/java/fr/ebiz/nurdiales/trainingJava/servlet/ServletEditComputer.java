package fr.ebiz.nurdiales.trainingJava.servlet;

import fr.ebiz.nurdiales.trainingJava.service.ComputerManager;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by ebiz on 19/04/17.
 */
@WebServlet(name = "ServletEditComputer")
public class ServletEditComputer extends HttpServlet {
    private ComputerManager manager;

    private static final String EDIT_COMPUTER_VIEW = "/WEB-INF/editComputer.jsp";
    private static final String CREATE_COMPUTER_VIEW = "/WEB-INF/addComputer.jsp";

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
