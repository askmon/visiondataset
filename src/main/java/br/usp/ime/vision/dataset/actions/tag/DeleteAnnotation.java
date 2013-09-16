package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.actions.album.ImageDetail;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to delete an {@link ImageAnnotation}.
 * 
 * @author Bruno Klava
 */
public class DeleteAnnotation extends ImageDetail {

    private static final long serialVersionUID = 1L;

    private int tagId;

    @Override
    public String execute() throws Exception {

        final ImageAnnotation annotation = DAOFactory.getTagDao()
                .getImageAnnotation(getImageId(), getTagId());

        if (annotation == null) {
            return INVALID_REQUEST;
        }

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (annotation.deleteTag()) {
            addActionMessage(Messages.getMessage("success.delete.annotation",
                    annotation.getTagName()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.delete.annotation",
                    annotation.getTagName()));
            return ERROR;
        }
    }

    public int getTagId() {
        return tagId;
    }

    public void setTagId(final int tagId) {
        this.tagId = tagId;
    }
}
