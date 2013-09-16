package br.usp.ime.vision.dataset.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.ResultSetDynaClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.interfaces.UserDAO;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Implementation of the {@link UserDAO} interface.
 * 
 * @author Bruno Klava
 */
@SuppressWarnings("rawtypes")
public class UserDAOImpl extends DAOImpl implements UserDAO {

    private static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);

    public boolean authorizeAccount(final String username) {
        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"authorizeAccount\"( ? )}");

            cs.setString(1, username);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Account of user {} authorized", username);
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public boolean changePassword(final String username, final String password) {
        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"changePassword\"( ? , ? , ? )}");

            cs.setString(1, username);
            cs.setString(2, password);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Password of user {} updated", username);
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public boolean changeUserActiveStatus(final int userId, final boolean active) {

        logger.debug("Changing account of user with id {} to {}", userId,
                active ? "active" : "inactive");

        try {

            final String call = "{call \"" + (active ? "" : "de")
                    + "activateAccount\"( ? )}";

            final CallableStatement cs = getConnection().prepareCall(call);

            cs.setInt(1, userId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Active status of user with id {} changed!", userId);
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean confirmEmail(final String username) {

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"confirmEmail\"( ? )}");

            cs.setString(1, username);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Email of user {} confirmed!", username);
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public boolean createUser(final User user) {

        logger.debug("Creating account {}", user);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"createUser\"( ? , ? , ? , ? , ? , ? , ? )}");

            cs.setString(1, user.getUsername());
            cs.setString(2, user.getPassword());
            cs.setString(3, user.getName());
            cs.setString(4, user.getEmail());
            cs.setBoolean(5, user.isEmailConfirmed());
            cs.setBoolean(6, user.isAccountAuthorized());
            cs.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Created user with id {}", rs.getInt("result"));
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public User getUserById(final int id) {

        User user = null;

        logger.debug("Getting user with id {}", id);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getUserById\"( ? )}");

            cs.setInt(1, id);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();
            if (rows.hasNext()) {
                user = getUserFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return user;

    }

    public User getUserByUsername(final String username) {

        User user = null;

        logger.debug("Getting user with username {}", username);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getUserByUsername\"( ? )}");

            cs.setString(1, username);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();
            if (rows.hasNext()) {
                user = getUserFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return user;

    }

    public List<User> listUsers() {

        final List<User> users = new ArrayList<User>();
        logger.debug("Getting users");

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getUsers\"( )}");

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                users.add(getUserFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return users;
    }

    public List<User> listUsersExcept(final int userId) {

        final List<User> users = new ArrayList<User>();
        logger.debug("Getting users except {}", userId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getUsersExcept\"( ? )}");

            cs.setInt(1, userId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                users.add(getUserFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return users;
    }

    public boolean removeUser(final String username) {

        logger.debug("Removing account {}", username);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"removeUser\"( ? )}");

            cs.setString(1, username);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("User removed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean setPermission(final String username, final int permission) {

        logger.debug("Setting permission {} to user {}",
                User.getPermissionString(permission), username);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"setUserPermission\"( ? , ? )}");

            cs.setString(1, username);
            cs.setInt(2, permission);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Permission setted!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public boolean updateUser(final User user) {

        logger.debug("Updating user account {}", user);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"updateUser\"( ? , ? , ? , ? , ? , ? , ? )}");

            cs.setInt(1, user.getId());
            cs.setString(2, user.getUsername());
            cs.setString(3, user.getPassword());
            cs.setString(4, user.getName());
            cs.setString(5, user.getEmail());
            cs.setBoolean(6, user.isEmailConfirmed());
            cs.setTimestamp(7, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("User account updated");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

}
