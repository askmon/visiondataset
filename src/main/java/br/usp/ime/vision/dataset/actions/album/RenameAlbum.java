package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to rename an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class RenameAlbum extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private String albumName;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getAlbumDao().renameAlbum(getAlbumId(), getAlbumName())) {
            addActionMessage(Messages.getMessage("success.rename.album",
                    getAlbumName()));
            return SUCCESS;

        } else {
            addActionError(Messages.getMessage("error.rename.album",
                    getAlbumName()));
            return INPUT;
        }

    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(final String albumName) {
        this.albumName = albumName;
    }

}
