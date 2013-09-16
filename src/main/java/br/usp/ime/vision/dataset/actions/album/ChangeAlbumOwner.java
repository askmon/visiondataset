package br.usp.ime.vision.dataset.actions.album;

import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to change the owner of an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class ChangeAlbumOwner extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int userId;

    private List<User> users;

    @Override
    public String execute() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getAlbumDao()
                .changeAlbumOwner(getAlbumId(), getUserId())) {
            addActionMessage(Messages.getMessage("success.change.owner"));
            return SUCCESS;

        } else {
            addActionError(Messages.getMessage("error.change.owner"));
            return INPUT;
        }

    }

    public int getUserId() {
        return userId;
    }

    public List<User> getUsers() {
        return users;
    }

    public String populateUsers() throws Exception {

        if (!isLoggedUserAdmin()) {
            return UNAUTHORIZED_ACTION;
        }

        setUsers(DAOFactory.getUserDao().listUsers());

        return SUCCESS;
    }

    public void setUserId(final int userId) {
        this.userId = userId;
    }

    public void setUsers(final List<User> users) {
        this.users = users;
    }

}
