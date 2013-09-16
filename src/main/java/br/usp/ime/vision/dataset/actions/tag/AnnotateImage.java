package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to add an {@link ImageAnnotation}.
 * 
 * @author Bruno Klava
 */
public class AnnotateImage extends AddImageTag {

    private static final long serialVersionUID = 1L;

    private int x;

    private int y;

    private int width;

    private int height;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getTagDao().addImageAnnotation(getImageId(),
                getTagName(), getX(), getY(), getWidth(), getHeight()) != 0) {
            addActionMessage(Messages.getMessage(
                    "success.add.image.annotation", getTagName()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.add.image.annotation"));
            return INPUT;
        }

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

}
