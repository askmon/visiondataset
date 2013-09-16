package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.Image;

/**
 * Action for {@link Image} visualization.
 * 
 * @author Bruno Klava
 */
public class ImageDetail extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int imageId;

    private Image image;

    @Override
    public String execute() throws Exception {

        if (getImage() == null) {
            return INVALID_REQUEST;
        }

        if (!isUserHasViewPermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    @Override
    public Album getAlbum() {
        if (album == null) {
            album = DAOFactory.getAlbumDao().getAlbum(getImage().getAlbumId());
        }
        return album;
    }

    @Override
    public int getAlbumId() {
        return getImage().getAlbumId();
    }

    public Image getImage() {
        if (image == null) {
            image = DAOFactory.getAlbumDao().getImage(getImageId());
        }
        return image;
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(final int imageId) {
        this.imageId = imageId;
    }

}
