package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.entities.ImageTag;

/**
 * Action to populate the view with necessary data for editing an
 * {@link ImageTag}.
 * 
 * @author Bruno Klava
 */
public class RenameImageTagData extends ImageTagAction {

    private static final long serialVersionUID = 1L;

    private int tagId;

    public int getTagId() {
        return tagId;
    }

    public void setTagId(final int tagId) {
        this.tagId = tagId;
    }

}
