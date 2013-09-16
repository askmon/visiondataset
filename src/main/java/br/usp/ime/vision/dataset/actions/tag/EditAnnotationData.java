package br.usp.ime.vision.dataset.actions.tag;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;

/**
 * Action to populate the view with necessary data for editing an
 * {@link ImageAnnotation}.
 * 
 * @author Bruno Klava
 */
public class EditAnnotationData extends ImageTagAction {

    private static final long serialVersionUID = 1L;

    private int tagId;

    private ImageAnnotation annotation;

    public ImageAnnotation getAnnotation() {
        if (annotation == null) {
            annotation = DAOFactory.getTagDao().getImageAnnotation(
                    getImageId(), getTagId());
        }
        return annotation;
    }

    public int getTagId() {
        return tagId;
    }

    public String getTagName() {
        return getAnnotation().getTagName();
    }

    public void setTagId(final int tagId) {
        this.tagId = tagId;
    }

}
