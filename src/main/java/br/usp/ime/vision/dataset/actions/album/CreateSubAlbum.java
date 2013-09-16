package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.SubAlbum;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to create an {@link SubAlbum}.
 * 
 * @author Bruno Klava
 */
public class CreateSubAlbum extends CreateSubAlbumData {

    private static final long serialVersionUID = 1L;

    private String albumName;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        final SubAlbum album = new SubAlbum();
        album.setName(getAlbumName());
        album.setOwnerId(getLoggedUser().getId());
        album.setParentId(getParentId());

        final int albumId = DAOFactory.getAlbumDao().createAlbum(album);

        if (albumId != 0) {
            addActionMessage(Messages.getMessage("success.create.album",
                    getAlbumName()));
            return SUCCESS;

        } else {
            addActionError(Messages.getMessage("error.create.album",
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
