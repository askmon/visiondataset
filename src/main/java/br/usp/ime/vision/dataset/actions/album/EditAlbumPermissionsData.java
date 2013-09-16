package br.usp.ime.vision.dataset.actions.album;

import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Action to populate the view with necessary data for changing the {@link User}
 * s creation/visualization permissions on an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class EditAlbumPermissionsData extends AlbumPermissions {

    private static final long serialVersionUID = 1L;

    private int albumId;

    private Album album;

    private List<User> usersWithPermission;

    private List<User> usersWithoutPermission;

    @Override
    public String execute() throws Exception {

        if (getAlbum() == null) {
            return INVALID_REQUEST;
        }

        final User user = getLoggedUser();
        if ((user == null)
                || !((getAlbum().getOwnerId() == user.getId()) || isLoggedUserAdmin())) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    public Album getAlbum() {
        if ((album == null) && (getAlbumId() != 0)) {
            album = DAOFactory.getAlbumDao().getAlbum(getAlbumId());
        }
        return album;
    }

    public int getAlbumId() {
        return albumId;
    }

    public List<User> getUsersWithoutPermission() {
        if (usersWithoutPermission == null) {
            usersWithoutPermission = DAOFactory.getAlbumDao()
                    .getUsersWithoutPermission(getAlbum().getId());
        }
        return usersWithoutPermission;
    }

    public List<User> getUsersWithPermission() {
        if (usersWithPermission == null) {
            usersWithPermission = DAOFactory.getAlbumDao()
                    .getUsersWithPermission(getAlbum().getId());
        }
        return usersWithPermission;
    }

    public void setAlbum(final Album album) {
        this.album = album;
    }

    public void setAlbumId(final int albumId) {
        if (albumId != this.albumId) {
            album = null;
        }
        this.albumId = albumId;
    }

}
