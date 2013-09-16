package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.actions.album.AlbumAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.AlbumTag;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to delete an {@link AlbumTag}.
 * 
 * @author Bruno Klava
 */
public class DeleteAlbumTag extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int tagId;

    @Override
    public String execute() throws Exception {

        final AlbumTag tag = DAOFactory.getTagDao().getAlbumTag(getAlbumId(),
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
