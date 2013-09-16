package br.usp.ime.vision.dataset.dao.interfaces;

import java.util.List;

import br.usp.ime.vision.dataset.entities.User;

/**
 * Interface for access to {@link User} related data.
 * 
 * @author Bruno Klava
 */
public interface UserDAO {

    /**
     * Authorizes the account of the {@link User} of the given
     * <code>username</code>.
     * 
     * @param username
     *            the name of a {@link User}
     * @return <code>true</code> if the authorization was set successfully
     */
    public boolean authorizeAccount(String username);

    /**
     * Changes the <code>password</code> of the {@link User} with the given
     * <code>username</code>.
     * 
     * @param username
     *            the name of a {@link User}
     * @param password
     *            the new <code>password</code> for the {@link User}
     * @return <code>true</code> if the <code>password</code> was updated
     *         successfully
     */
    public boolean changePassword(String username, String password);

    /**
     * Change the active status of the {@link User} with the given
     * <code>userId</code>.
     * 
     * @param userId
     *            the id of an existing {@link User}
     * @param active
     *            if the account must be activated or not
     * @return <code>true</code> if the {@link User} active status change was
     *         successful
     */
    public boolean changeUserActiveStatus(int userId, boolean active);

    /**
     * Confirms the e-mail of the {@link User} of the given
     * <code>username</code>.
     * 
     * @param username
     *            the name of a {@link User}
     * @return <code>true</code> if the email confirmation was set successfully
     */
    public boolean confirmEmail(String username);

    /**
     * Adds the <code>user</code> in the system.
     * 
     * @param user
     *            a new {@link User}
     * @return <code>true</code> if the {@link User} creation was successful
     */
    public boolean createUser(User user);

    /**
     * Searches for the {@link User} with the given <code>id</code>.
     * 
     * @param id
     *            the id of an {@link User}
     * @return the {@link User} with the given <code>id</code>,
     *         <code>null</code> if there is no {@link User} with the given
     *         <code>id</code>
     */
    public User getUserById(int id);

    /**
     * Searches for the {@link User} with the given <code>username</code>.
     * 
     * @param username
     *            the name of a {@link User}
     * @return the {@link User} with the given <code>username</code>,
     *         <code>null</code> if there is no {@link User} with the given
     *         <code>username</code>
     */
    public User getUserByUsername(String username);

    /**
     * Returns a <code>List</code> with the existing {@link User}s.
     * 
     * @return a <code>List</code> with the existing {@link User}s
     */
    public List<User> listUsers();

    /**
     * Returns a <code>List</code> with the existing {@link User}s, except for
     * the {@link User} with the given <code>userId</code>.
     * 
     * @param userId
     *            the id of a {@link User}
     * @return a <code>List</code> with the existing {@link User}s, except for
     *         the {@link User} with the given <code>userId</code>
     */
    public List<User> listUsersExcept(int userId);

    /**
     * Removes a {@link User} with the given <code>username</code> from the
     * system.
     * 
     * @param username
     *            the username of an existing {@link User}
     * @return <code>true</code> if the {@link User} deletion was successful
     */
    public boolean removeUser(String username);

    /**
     * Sets the <code>permission</code> of the {@link User} of the given
     * <code>username</code>.
     * 
     * @param username
     *            the name of a {@link User}
     * @param permission
     * @return <code>true</code> if the <code>permission</code> was set
     *         successfully
     */
    public boolean setPermission(String username, int permission);

    /**
     * Updates the <code>user</code> account data.
     * 
     * @param user
     *            an existing {@link User}
     * @return <code>true</code> if the {@link User} account data was updated
     *         successfully
     */
    public boolean updateUser(User user);

}
