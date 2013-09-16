package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.entities.Album;

/**
 * Action for {@link Album} visualization.
 * 
 * @author Bruno Klava
 */
public class AlbumDetail extends AlbumAction {

    private static final long serialVersionUID = 1L;

    @Override
    public String execute() throws Exception {

        if (getAlbum() == null) {
            return INVALID_REQUEST;
        }

        if (!isUserHasViewPermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }
}
