package br.usp.ime.vision.dataset.actions.admin;

import java.util.List;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Action to list all the {@link User}s.
 * 
 * @author Bruno Klava
 */
public class ListUsers extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private List<User> users;

    @Override
    public String execute() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        setUsers(DAOFactory.getUserDao().listUsers());
        return SUCCESS;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

}
