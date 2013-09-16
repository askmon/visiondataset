package br.usp.ime.vision.dataset.actions.album;

import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.SubAlbum;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to delete an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class DeleteAlbum extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private int parentId;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        if (getAlbum().deleteAlbum()) {
            addActionMessage(Messages.getMessage("success.delete.album",
                    getAlbum().getName()));

            if (getAlbum() instanceof SubAlbum) {
                final SubAlbum subAlbum = (SubAlbum) getAlbum();
                setParentId(subAlbum.getParentId());
                return SUCCESS;
            } else {
                return "listAlbums";
            }

        } else {
            addActionError(Messages.getMessage("error.delete.album", getAlbum()
                    .getName()));
            return ERROR;
        }
    }

    public int getParentId() {
        return parentId;
    }

    public void setParentId(final int parentId) {
        this.parentId = parentId;
    }
}
