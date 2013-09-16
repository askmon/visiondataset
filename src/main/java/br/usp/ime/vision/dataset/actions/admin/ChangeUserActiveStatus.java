package br.usp.ime.vision.dataset.actions.admin;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action for change status {@link User} accounts.
 * 
 * @author Bruno Klava
 */
public class ChangeUserActiveStatus extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private int userId;

    private boolean active;

    @Override
    public String execute() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getUserDao().changeUserActiveStatus(getUserId(),
                isActive())) {
            if (isActive()) {
                addActionMessage(Messages
                        .getMessage("success.activate.account"));
            } else {
                addActionMessage(Messages
                        .getMessage("success.deactivate.account"));
            }
            return SUCCESS;
        } else {
            if (isActive()) {
                addActionError(Messages.getMessage("error.activate.account"));
            } else {
                addActionError(Messages.getMessage("error.deactivate.account"));
            }
            return ERROR;
        }
    }

    public int getUserId() {
        return userId;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(final boolean active) {
        this.active = active;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

}
