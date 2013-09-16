package br.usp.ime.vision.dataset.actions.user;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.CryptoUtils;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to change the {@link User} password.
 * 
 * @author Bruno Klava
 */
public class ChangePassword extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String password;

    private String passwordConfirmation;

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() == null) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getUserDao().changePassword(
                getLoggedUser().getUsername(),
                CryptoUtils.encrypt(getPassword()))) {
            addActionMessage(Messages.getMessage("success.password.update"));
            getSession().put(
                    USER_SESSION_PARAMETER,
                    DAOFactory.getUserDao().getUserByUsername(
                            getLoggedUser().getUsername()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.password.update"));
            return INPUT;
        }

    }

    public String getPassword() {
        return password;
    }

    public String getPasswordConfirmation() {
        return passwordConfirmation;
    }

    public void setPassword(final String password) {
        this.password = password;
    }

    public void setPasswordConfirmation(final String passwordConfirmation) {
        this.passwordConfirmation = passwordConfirmation;
    }

}
