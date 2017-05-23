package fr.ebiz.nurdiales.trainingJava.exceptions;

public class DAOComputerException extends DAOException {
    /**
     * constructor who call the super constructor.
     * @param message message for the error.
     */
    public DAOComputerException(String message) {
        super("[DAOComputerException] : " + message);
    }
}