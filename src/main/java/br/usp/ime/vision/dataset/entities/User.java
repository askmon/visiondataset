package br.usp.ime.vision.dataset.entities;

import java.util.Date;

import br.usp.ime.vision.dataset.util.Constants;
import br.usp.ime.vision.dataset.util.CryptoUtils;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Represents an {@link User} in the system.
 * 
 * @author Bruno Klava
 */
public class User {

    /**
     * Returns the <code>String</code> representation of an {@link User}
     * <code>permission</code>.
     * 
     * @return the <code>String</code> representation of an {@link User}
     *         <code>permission</code>
     */
    public static String getPermissionString(final int permission) {
        switch (permission) {
        case Constants.ADMIN_PERMISSION:
            return Messages.getMessage("permission.admin");
        case Constants.CREATE_PERMISSION:
            return Messages.getMessage("permission.create");
        default:
        case Constants.VIEW_PERMISSION:
            return Messages.getMessage("permission.view");
        }
    }

    private int id;

    private String username;

    private String name;

    private String email;

    private boolean emailConfirmed;

    private boolean accountAuthorized;

    private String password;

    private Date dateCreation;

    private Date dateUpdate;

    private int permission;

    private boolean active;

    public User() {

    }

    /**
     * Generates the confirmation code (derived from <code>email</code> and
     * <code>password</code>) associated to this {@link User}.
     * 
     * @return the confirmation code (derived from <code>email</code> and
     *         <code>password</code>) associated to this {@link User}
     */
    public String getConfirmationCode() {
        return CryptoUtils.encrypt(getEmail() + getPassword());
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    /**
     * Returns an <code>String</code> description of this {@link User}.
     * 
     * @return an <code>String</code> description of this {@link User}
     */
    public String getDescription() {
        return getName() + " (" + getUsername() + ") " + getEmail();
    }

    public String getEmail() {
        return email;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getPassword() {
        return password;
    }

    public int getPermission() {
        return permission;
    }

    /**
     * Returns the <code>String</code> representation of this {@link User}
     * <code>permission</code>.
     * 
     * @return the <code>String</code> representation of this {@link User}
     *         <code>permission</code>
     */
    public String getPermissionString() {
        return getPermissionString(permission);
    }

    // TODO refactor the view to use fn and remove this method
    public String getPermissionStringLowerCase() {
        return getPermissionString().toLowerCase();
    }

    public String getUsername() {
        return username;
    }

    public boolean isAccountAuthorized() {
        return accountAuthorized;
    }

    public boolean isActive() {
        return active;
    }

    /**
     * Returns if this {@link User} has {@link Constants.ADMIN_PERMISSION}
     * <code>permission</code>.
     * 
     * @return <code>true</code> if this {@link User} has
     *         {@link Constants.ADMIN_PERMISSION} <code>permission</code>
     */
    public boolean isAdmin() {
        return permission == Constants.ADMIN_PERMISSION;
    }

    /**
     * Returns if this {@link User} has {@link Constants.CREATE_PERMISSION}
     * <code>permission</code>.
     * 
     * @return <code>true</code> if this {@link User} has
     *         {@link Constants.CREATE_PERMISSION} <code>permission</code>
     */
    public boolean isCreatePermission() {
        return permission >= Constants.CREATE_PERMISSION;
    }

    public boolean isEmailConfirmed() {
        return emailConfirmed;
    }

    /**
     * Returns if this {@link User} <code>email</code> is associated to an IME's
     * Vision Research Group account.
     * 
     * @return <code>true</code> if this {@link User} <code>email</code> is
     *         associated to an IME's Vision Research Group account
     */
    public boolean isVisionUser() {
        return getEmail().endsWith("@vision.ime.usp.br");
    }

    /**
     * Tests <code>otherPassword</code> has the same crypto signature.
     * 
     * @param otherPassword
     * @return
     */
    public boolean samePassword(final String otherPassword) {
        return CryptoUtils.encrypt(otherPassword).equals(getPassword());
    }

    public void setAccountAuthorized(final boolean accountAuthorized) {
        this.accountAuthorized = accountAuthorized;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public void setDateCreation(final Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateUpdate(final Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public void setEmail(final String email) {
        this.email = email;
    }

    public void setEmailConfirmed(final boolean emailConfirmed) {
        this.emailConfirmed = emailConfirmed;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPermission(final int permission) {
        this.permission = permission;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "User [id=" + id + ", username=" + username + ", permission="
                + getPermissionString() + ", name=" + name + ", email=" + email
                + ", emailConfirmed=" + emailConfirmed + ", accountAuthorized="
                + accountAuthorized + ", active=" + active + ", dateCreation="
                + dateCreation + ", dateUpdate=" + dateUpdate + "]";
    }

}
