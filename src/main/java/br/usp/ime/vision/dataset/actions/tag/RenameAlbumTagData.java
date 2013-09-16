package br.usp.ime.vision.dataset.actions.tag;

import java.util.List;

import br.usp.ime.vision.dataset.actions.album.AlbumAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.AlbumTag;

/**
 * Action to populate the view with necessary data for editing an
 * {@link AlbumTag}.
 * 
 * @author Bruno Klava
 */
public class RenameAlbumTagData extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int tagId;

    private List<String> tags;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    public int getTagId() {
        return tagId;
    }

    public List<String> getTags() {
        if (tags == null) {
            tags = DAOFactory.getTagDao().getTagsNames();
        }
        return tags;
    }

    public void setTagId(final int tagId) {
        this.tagId = tagId;
    }

}
