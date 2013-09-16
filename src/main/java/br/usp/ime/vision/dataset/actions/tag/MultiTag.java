package br.usp.ime.vision.dataset.actions.tag;

import org.apache.commons.lang.xwork.StringUtils;

import br.usp.ime.vision.dataset.actions.album.AlbumAction;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Tag;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to add {@link Tag}s to multiple content at the same time.
 * 
 * @author Bruno Klava
 */
public class MultiTag extends AlbumAction {

    private static final long serialVersionUID = 1L;

    private String albunsTags;

    private String imagesTags;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        boolean success = true;

        if (!StringUtils.isEmpty(albunsTags)) {
            for (final String i : albunsTags.split("##")) {
                final String[] data = i.split(":");
                for (final String tag : data[1].split(",")) {
                    if (DAOFactory.getTagDao().addAlbumTag(
                            Integer.parseInt(data[0]), tag) == 0) {
                        success = false;
                    }
                }
            }
        }

        if (!StringUtils.isEmpty(imagesTags)) {
            for (final String i : imagesTags.split("##")) {
                final String[] data = i.split(":");
                for (final String tag : data[1].split(",")) {
                    if (DAOFactory.getTagDao().addImageTag(
                            Integer.parseInt(data[0]), tag) == 0) {
                        success = false;
                    }
                }
            }
        }

        if (success) {
            addActionMessage(Messages.getMessage("success.multi.tag"));
            return SUCCESS;
        } else {
            addActionError(Messages.getMessage("error.multi.tag"));
            return INPUT;
        }

    }

    public String getAlbunsTags() {
        return albunsTags;
    }

    public String getImagesTags() {
        return imagesTags;
    }

    public void setAlbunsTags(final String albunsTags) {
        this.albunsTags = albunsTags;
    }

    public void setImagesTags(final String imagesTags) {
        this.imagesTags = imagesTags;
    }

}
