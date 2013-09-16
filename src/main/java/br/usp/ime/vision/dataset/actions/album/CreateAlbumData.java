package br.usp.ime.vision.dataset.actions.album;

import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Action to populate the view with necessary data for {@link Album} creation.
 * 
 * @author Bruno Klava
 */
public class CreateAlbumData extends AlbumPermissions {

    private static final long serialVersionUID = 1L;

    private List<User> users;

    @Override
    public String execute() throws Exception {

        final User user = getLoggedUser();

        if ((user == null) || !user.isCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    public List<User> getUsers() {
        if (users == null) {
            users = DAOFactory.getUserDao().listUsersExcept(
                    getLoggedUser().getId());
        }
        return users;
    }

}
