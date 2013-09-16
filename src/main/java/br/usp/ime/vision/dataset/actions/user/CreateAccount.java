package br.usp.ime.vision.dataset.actions.user;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.CryptoUtils;
import br.usp.ime.vision.dataset.util.Email;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to create an {@link User} account.
 * 
 * @author Bruno Klava
 */
public class CreateAccount extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private User user;

    private String password;

    private String passwordConfirmation;

    @Override
    public String execute() throws Exception {

        getUser().setPassword(CryptoUtils.encrypt(getPassword()));

        if (!((getLoggedUser() == null) || isLoggedUserAdmin())) {
            return UNAUTHORIZED_ACTION;
        }

        if (isLoggedUserAdmin()) {
            getUser().setEmailConfirmed(true);
            getUser().setAccountAuthorized(true);
        }

        if (getUser().isVisionUser()) {
            getUser().setAccountAuthorized(true);
        }

        if (DAOFactory.getUserDao().createUser(getUser())) {

            if (isLoggedUserAdmin()) {

                addActionMessage(Messages.getMessage("success.create.user",
                        getUser().getUsername()));
                return "successAdmin";

            } else {

                addActionMessage(Messages.getMessage("success.create.account",
                        getUser().getEmail()));

                sendConfirmationEmail(getUser());

                if (!getUser().isAccountAuthorized()) {
                    final String subject = Messages
                            .getMessage("emailNewAccount.subject");
                    final String message = Messages.getMessage(
                            "emailNewAccount.message", getUser().getUsername(),
                            getUser().getName(), getUser().getEmail());
                    Email.sendEmailToAdmin(subject, message);
                }

                return SUCCESS;
            }

        } else {
            addActionError(Messages.getMessage("error.username.taken",
                    user.getUsername()));
            getUser().setUsername(null);
            return INPUT;
        }

    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public User getUser() {
        return user;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(final String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

    public void setUser(final User user) {
        this.user = user;
    }

}
