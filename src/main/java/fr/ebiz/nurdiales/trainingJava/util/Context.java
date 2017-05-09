package fr.ebiz.nurdiales.trainingJava.util;

import java.sql.Connection;

/**
 * Created by ebiz on 28/04/17.
 */
public class Context {
    private Connection connection = null;

    public Connection getConnection() {
        return connection;
    }

    public void setConnection(Connection connection) {
        this.connection = connection;
    }
}