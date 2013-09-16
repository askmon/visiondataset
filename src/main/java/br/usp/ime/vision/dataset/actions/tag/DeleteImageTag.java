package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.actions.album.ImageDetail;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageTag;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to delete an {@link ImageTag}.
 * 
 * @author Bruno Klava
 */
public class DeleteImageTag extends ImageDetail {

    private static final long serialVersionUID = 1L;

    private int tagId;

    @Override
    public String execute() throws Exception {

        final ImageTag tag = DAOFactory.getTagDao().getImageTag(getImageId(),
                getTagId());

        if (tag == null) {
            return INVALID_REQUEST;
        }

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (tag.deleteTag()) {
            addActionMessage(Messages.getMessage("success.delete.tag",
                    tag.getTagName()));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.delete.tag",
                    tag.getTagName()));
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
