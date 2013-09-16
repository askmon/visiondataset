package br.usp.ime.vision.dataset.actions.user;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Action to verify the permission necessary for editing an {@link User}
 * account.
 * 
 * @author Bruno Klava
 */
public class EditProfileData extends AbstractAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {

        if (getLoggedUser() == null) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    public User getUser() {
        return getLoggedUser();
    }

}
