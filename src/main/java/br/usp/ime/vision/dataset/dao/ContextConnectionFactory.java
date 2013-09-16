package br.usp.ime.vision.dataset.dao;

import java.sql.Connection;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

/**
 * Provides {@link Connection}s for data access.
 * 
 * @author Bruno Klava e RafaelLopes
 */
public class ContextConnectionFactory implements ConnectionFactory {

    private static Logger logger = Logger
            .getLogger(ContextConnectionFactory.class);

    private static String dataSource = "java:comp/env/jdbc/visionDataset";

    public Connection getConnection() {

        Connection connection = null;

        try {
            final Context jndiCntx = new InitialContext();
            final DataSource ds = (javax.sql.DataSource) jndiCntx
                    .lookup(dataSource);

            connection = ds.getConnection();
            connection
                    .setTransactionIsolation(Connection.TRANSACTION_SERIALIZABLE);

            logger.debug("Successfully connected to the database. Datasource: "
                    + dataSource);

        } catch (final Exception e) {
            logger.error(e);
        }

        return connection;
    }

}
