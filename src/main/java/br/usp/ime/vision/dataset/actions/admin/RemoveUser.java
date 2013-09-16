package br.usp.ime.vision.dataset.actions.admin;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to delete an {@link User} account.
 * 
 * @author Bruno Klava
 */
public class RemoveUser extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String username;

    @Override
    public String execute() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getUserDao().removeUser(getUsername())) {
            addActionMessage(Messages.getMessage("success.remove.user",
                    getUsername()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.remove.user",
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
