package br.usp.ime.vision.dataset.actions.album;

import java.util.List;

import br.usp.ime.vision.dataset.actions.AbstractAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Action to list all the root {@link Album}s that the logged {@link User} has
 * visualization permission.
 * 
 * @author Bruno Klava
 */
public class ListAlbums extends AbstractAction {

    private static final long serialVersionUID = 1L;

    private List<Album> albums;

    @Override
    public String execute() throws Exception {

        final User user = getLoggedUser();

        if (user == null) {
            setAlbums(DAOFactory.getAlbumDao().getPublicAlbums());
        } else if (isLoggedUserAdmin()) {
            setAlbums(DAOFactory.getAlbumDao().getAllAlbums());
        } else {
            setAlbums(DAOFactory.getAlbumDao().getAlbumsUser(user.getId()));
        }

        return SUCCESS;

    }

    public List<Album> getAlbums() {
        return albums;
    }

    public void setAlbums(final List<Album> albums) {
        this.albums = albums;
    }

}
