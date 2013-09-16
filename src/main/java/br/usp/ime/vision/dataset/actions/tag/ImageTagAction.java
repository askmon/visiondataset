package br.usp.ime.vision.dataset.actions.tag;

import java.util.List;

import br.usp.ime.vision.dataset.actions.album.ImageDetail;
import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.Tag;

/**
 * Action to populate the view with {@link Tag}s.
 * 
 * @author Bruno Klava
 */
public class ImageTagAction extends ImageDetail {

    private static final long serialVersionUID = 1L;

    private List<String> tags;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        return SUCCESS;

    }

    public List<String> getTags() {
        if (tags == null) {
            tags = DAOFactory.getTagDao().getTagsNames();
        }
        return tags;
    }

}
