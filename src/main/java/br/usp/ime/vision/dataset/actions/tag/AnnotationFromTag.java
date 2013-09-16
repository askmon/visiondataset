package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.actions.album.ImageDetail;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;
import br.usp.ime.vision.dataset.entities.ImageTag;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to convert an {@ImageTag} to {@link ImageAnnotation}.
 * 
 * @author Bruno Klava
 */
public class AnnotationFromTag extends ImageDetail {

    private static final long serialVersionUID = 1L;

    private int tagId;

    private ImageTag imageTag;

    private int x;

    private int y;

    private int width;

    private int height;

    public String checkPermission() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (DAOFactory.getTagDao().addImageAnnotation(getImageId(),
                getImageTag().getTagName(), getX(), getY(), getWidth(),
                getHeight()) != 0) {

            if (getImageTag().deleteTag()) {

                addActionMessage(Messages.getMessage(
                        "success.annotation.from.tag", getImageTag()
                                .getTagName()));

            } else {
                addActionError(Messages.getMessage("error.delete.tag",
                        getImageTag().getTagName()));
                return ERROR;
            }

        } else {
            addActionError(Messages.getMessage("error.annotation.from.tag"));
            return INPUT;
        }

        return SUCCESS;

    }

    public int getHeight() {
        return height;
    }

    public ImageTag getImageTag() {
        if (imageTag == null) {
            imageTag = DAOFactory.getTagDao().getImageTag(getImageId(),
                    getTagId());
        }
        return imageTag;
    }

    public int getTagId() {
        return tagId;
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

    public void setTagId(final int tagId) {
        this.tagId = tagId;
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
    public void validate() {
        // TODO Auto-generated method stub
        super.validate();
    }

}
