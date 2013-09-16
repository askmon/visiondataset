package br.usp.ime.vision.dataset.actions.admin;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Email;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to authorize {@link User} account creation.
 * 
 * @author Bruno Klava
 */
public class AuthorizeAccount extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String username;

    @Override
    public String execute() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getUserDao().authorizeAccount(getUsername())) {
            addActionMessage(Messages.getMessage("success.authorize.account",
                    getUsername()));

            final User user = DAOFactory.getUserDao().getUserByUsername(
                    getUsername());
            Email.sendEmail(user.getEmail(),
                    Messages.getMessage("emailAccountAuthorized.subject"),
                    Messages.getMessage("emailAccountAuthorized.message"));

            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.authorize.account",
                    getUsername()));
            return ERROR;
        }
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
