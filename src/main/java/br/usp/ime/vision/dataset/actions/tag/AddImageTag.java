package br.usp.ime.vision.dataset.actions.tag;

import org.apache.commons.lang.xwork.StringUtils;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageTag;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Action to add an {@link ImageTag}.
 * 
 * @author Bruno Klava
 */
public class AddImageTag extends ImageTagAction {

    private static final long serialVersionUID = 1L;

    private String tagName;

    @Override
    public String execute() throws Exception {

        if (!isUserHasCreatePermission()) {
            return UNAUTHORIZED_ACTION;
        }

        for (String tag : tagName.split(",")) {

            tag = tag.trim();

            if (StringUtils.isEmpty(tag)) {
                continue;
            }

            if (DAOFactory.getTagDao().addImageTag(getImageId(), tag) != 0) {
                addActionMessage(Messages.getMessage("success.add.image.tag",
                        tag));
            } else {
                addActionError(Messages.getMessage("error.add.image.tag"));
                return INPUT;
            }

        }

        return SUCCESS;

    }

    public String getTagName() {
        return tagName;
    }

    public void setTagName(final String tagName) {
        this.tagName = tagName;
    }

}
