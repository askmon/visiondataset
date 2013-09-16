package br.usp.ime.vision.dataset.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface ConnectionFactory {

    /**
     * Returns a {@link Connection} with the configured database.
     * 
     * @return a {@link Connection} with the configured database
     * @throws SQLException
     */
    public Connection getConnection() throws SQLException;
}
