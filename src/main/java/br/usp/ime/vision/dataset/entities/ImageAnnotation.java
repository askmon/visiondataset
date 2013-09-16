package br.usp.ime.vision.dataset.entities;

import br.usp.ime.vision.dataset.dao.DAOFactory;

/**
 * Represents an {@link ImageAnnotation} associated to an {@link Image}.
 * 
 * @author Bruno Klava
 */
public class ImageAnnotation extends ImageTag {

    private int x;

    private int y;

    private int width;

    private int height;

    @Override
    public boolean deleteTag() {
        return DAOFactory.getTagDao().deleteImageAnnotation(getImageId(),
                getId());
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setHeight(final int height) {
        this.height = height;
    }

    public void setWidth(final int width) {
        this.width = width;
    }

    public void setX(final int x) {
        this.x = x;
    }

    public void setY(final int y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "ImageAnnotation [x=" + x + ", y=" + y + ", width=" + width
                + ", height=" + height + ", getTagName()=" + getTagName()
                + ", getId()=" + getId() + ", getImageId()=" + getImageId()
                + "]";
    }

}
