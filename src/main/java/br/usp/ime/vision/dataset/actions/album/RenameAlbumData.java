package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.entities.Album;

/**
 * Action to populate the view with necessary data for renaming an {@link Album}
 * .
 * 
 * @author Bruno Klava
 */
public class RenameAlbumData extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private String albumName;

    @Override
    public String execute() throws Exception {

        setAlbumName(getAlbum().getName());

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    public String getAlbumName() {
        return albumName;
    }

    public void setAlbumName(final String albumName) {
        this.albumName = albumName;
    }

}
