package fr.ebiz.nurdiales.trainingJava.exceptions;

public class CompanyDAOException extends Exception {    /**
     * constructor who call the super constructor.
     * @param message message for the error.
     */
    public CompanyDAOException(String message) {
        super("[CompanyDAOException] : " + message);
    }
}