package fr.ebiz.nurdiales.trainingJava.exceptions;

public class DAOException extends Exception {

    /**
     * Default execption for DAO.
     */
    public DAOException() {
        super("Exception base de donnée");
    }

    /**
     * Default execption for DAO.
     * @param msg exception message.
     */
    public DAOException(String msg) {
        super(msg);
    }
}
