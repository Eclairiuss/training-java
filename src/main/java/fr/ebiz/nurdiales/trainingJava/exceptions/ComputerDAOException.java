package fr.ebiz.nurdiales.trainingJava.exceptions;

public class ComputerDAOException extends DAOException {
    /**
     * constructor who call the super constructor.
     * @param message message for the error.
     */
    public ComputerDAOException(String message) {
        super("[ComputerDAOException] : " + message);
    }
}