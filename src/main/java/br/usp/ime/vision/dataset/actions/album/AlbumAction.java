package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Abstract action with {@link Album} related common methods.
 * 
 * @author Bruno Klava
 */
public abstract class AlbumAction extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private int albumId;

    protected Album album;

    public Album getAlbum() {
        if (album == null) {
            album = DAOFactory.getAlbumDao().getAlbum(getAlbumId());
        }
        return album;
    }

    public int getAlbumId() {
        return albumId;
    }

    /**
     * Verifies if the logged {@link User} has create permission on the selected
     * {@link Album}.
     * 
     * @return <code>true</code> if the logged {@link User} has create
     *         permission on the selected {@link Album}
     */
    public boolean isUserHasCreatePermission() {

        if (isLoggedUserAdmin()) {
            return true;
        }

        final User user = getLoggedUser();

        if (user == null) {
            return false;
        }

        if (getAlbum().isUsersCreate()) {
            return true;
        }

        if (getAlbum().isRestrictedCreate()
                && DAOFactory.getAlbumDao().userHasPermissionOnAlbum(
                        getAlbum().getRootAlbum().getId(), user.getId())) {
            return true;
        }

        return false;

    }

    /**
     * Verifies if the logged {@link User} has view permission on the selected
     * {@link Album}.
     * 
     * @return <code>true</code> if the logged {@link User} has view permission
     *         on the selected {@link Album}
     */
    public boolean isUserHasViewPermission() {

        if (isLoggedUserAdmin()) {
            return true;
        }

        if (getAlbum().isPublicView()) {
            return true;
        }

        final User user = getLoggedUser();

        if (user == null) {
            return false;
        }

        if (getAlbum().isUsersView()) {
            return true;
        }

        if (getAlbum().isRestrictedView()
                && DAOFactory.getAlbumDao().userHasPermissionOnAlbum(
                        getAlbum().getRootAlbum().getId(), user.getId())) {
            return true;
        }

        return false;

    }

    public void setAlbumId(final int albumId) {
        this.albumId = albumId;
    }

}
