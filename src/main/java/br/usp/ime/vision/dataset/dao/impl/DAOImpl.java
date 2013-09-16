package br.usp.ime.vision.dataset.dao.impl;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.ConnectionFactory;
import br.usp.ime.vision.dataset.dao.ContextConnectionFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Configs;

/**
 * Abstract class for data access
 * 
 * @author BrunoKlava and RafaelLopes
 */
public abstract class DAOImpl {

    private static Logger logger = LoggerFactory.getLogger(DAOImpl.class);

    private static Connection connection;

    private static int callers;

    private ConnectionFactory connectionFactory;

    public DAOImpl() {
        try {

            final String factoryName = Configs.getConnectionFactoryClassName();
            logger.debug("connection factory creating a" + factoryName);
            final Class<?> factoryClass = Class.forName(factoryName);
            connectionFactory = (ConnectionFactory) factoryClass.newInstance();
        } catch (final Exception e) {
            logger.error("Cant creat factory:", e);
            logger.debug("connection factory fallback: creating a ContextConnectionFactory");
            connectionFactory = new ContextConnectionFactory();
        }
    }

    /**
     * Closes the active connection to the database if it is not being used.
     * 
     * @throws SQLException
     */
    protected synchronized void closeConnection() throws SQLException {
        callers--;
        if ((connection != null) && !connection.isClosed() && (callers == 0)) {
            logger.debug("Closing connection");
            connection.close();
        }
    }

    private Connection createConnection() throws SQLException {
        return connectionFactory.getConnection();
    }

    /**
     * Returns a {@link Connection} with the database.
     * 
     * @return a {@link Connection} with the database
     * @throws SQLException
     */
    protected synchronized Connection getConnection() throws SQLException {
        callers++;
        if ((connection == null) || connection.isClosed()) {
            connection = createConnection();
            logger.debug("Created new connection to database");
        } else {
            logger.debug("Using existing connection");
        }
        return connection;
    }

    /**
     * Returns an {@link User} with the data provided by the <code>rs</code>.
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link User} from the database
     */
    protected User getUserFromDB(final ResultSet rs, final Object row) {
        User user = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                user = new User();
                BeanUtils.copyProperties(user, bean);
                // XXX BeanUtils bug with cammelCase?
                user.setDateCreation(rs.getTimestamp("dateCreation"));
                user.setDateUpdate(rs.getTimestamp("dateUpdate"));
                user.setEmailConfirmed(rs.getBoolean("emailConfirmed"));
                user.setAccountAuthorized(rs.getBoolean("accountAuthorized"));
                logger.debug("Found user: {}", user);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return user;
    }

}
