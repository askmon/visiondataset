package br.usp.ime.vision.dataset.actions.admin;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to change the permissions of an {@link User}.
 * 
 * @author Bruno Klava
 */
public class SetUserPermission extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private String username;

    private int permission;

    @Override
    public String execute() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getUserDao().setPermission(getUsername(),
                getPermission())) {
            addActionMessage(Messages.getMessage("success.permission.user",
                    User.getPermissionString(getPermission()), getUsername()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.permission.user",
                    User.getPermissionString(getPermission()), getUsername()));
            return ERROR;
        }
    }

    public int getPermission() {
        return permission;
    }

    public String getUsername() {
        return username;
    }

    public void setPermission(final int permission) {
        this.permission = permission;
    }

    public void setUsername(final String username) {
        this.username = username;
    }

}
