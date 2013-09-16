package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to delete an {@link Image}.
 * 
 * @author Bruno Klava
 */
public class DeleteImage extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int imageId;

    @Override
    public String execute() throws Exception {

        final Album album = DAOFactory.getAlbumDao().getAlbum(getAlbumId());
        final Image image = DAOFactory.getAlbumDao().getImage(getImageId());

        if ((album == null) || (image == null)
                || (image.getAlbumId() != getAlbumId())) {
            return INVALID_REQUEST;
        }

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (image.deleteImage()) {
            addActionMessage(Messages.getMessage("success.delete.image",
                    getImageId()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.delete.image",
                    getImageId()));
            return ERROR;
        }
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(final int imageId) {
        this.imageId = imageId;
    }
}
