package br.usp.ime.vision.dataset.dao.impl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


import br.usp.ime.vision.dataset.dao.ConnectionFactory;

public class ConnectionFactoryMockup implements ConnectionFactory {

    public Connection getConnection() throws SQLException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
        String url = "jdbc:postgresql://127.0.0.1:5432/testvisionds?user=visionDataset&password=visionDataset";
        Connection conn;
        conn = DriverManager.getConnection(url);
        return conn;
    }
    

}
