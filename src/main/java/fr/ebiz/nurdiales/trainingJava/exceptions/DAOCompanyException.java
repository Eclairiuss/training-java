package fr.ebiz.nurdiales.trainingJava.exceptions;

public class DAOCompanyException extends DAOException {
    /**
     * constructor who call the super constructor.
     * @param message message for the error.
     */
    public DAOCompanyException(String message) {
        super("[DAOCompanyException] : " + message);
    }
}