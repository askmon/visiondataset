package br.usp.ime.vision.dataset.entities;

import br.usp.ime.vision.dataset.dao.DAOFactory;

/**
 * Represents an {@link Tag} associated to an {@link Image}.
 * 
 * @author Bruno Klava
 */
public class ImageTag extends Tag {

    private int imageId;

    @Override
    public boolean deleteTag() {
        return DAOFactory.getTagDao().deleteImageTag(getImageId(), getId());
    }

    public int getImageId() {
        return imageId;
    }

    public void setImageId(final int imageId) {
        this.imageId = imageId;
    }

    @Override
    public String toString() {
        return "ImageTag [" + getTagName() + ", id=" + getId() + ", imageId="
                + imageId + "]";
    }

}
