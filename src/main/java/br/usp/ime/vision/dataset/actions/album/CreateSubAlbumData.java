package br.usp.ime.vision.dataset.actions.album;

import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.SubAlbum;

/**
 * Action to populate the view with necessary data for {@link SubAlbum}
 * creation.
 * 
 * @author Bruno Klava
 */
public class CreateSubAlbumData extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int parentId;

    private Album parent;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    @Override
    public Album getAlbum() {
        return getParent();
    }

    public Album getParent() {
        if (parent == null) {
            parent = DAOFactory.getAlbumDao().getAlbum(getParentId());
        }
        return parent;
    }

    public int getParentId() {
        return parentId;
    }

    public List<Album> getParents() {
        return getParent().getAlbumsChain();
    }

    public void setParentId(final int parentId) {
        this.parentId = parentId;
    }

}
