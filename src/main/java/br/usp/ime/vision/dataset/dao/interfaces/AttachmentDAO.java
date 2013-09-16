package br.usp.ime.vision.dataset.dao.interfaces;

import java.util.List;

import br.usp.ime.vision.dataset.entities.Attachment;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.ImageAttachment;

/**
 * @author RafaelLopes
 * 
 */
public interface AttachmentDAO {

    /**
     * Add an attachment to a image.
     * 
     * @param imageId
     *            <code> id </code> of the {@link Image}.
     * @param name
     *            {@link Attachment} name.
     * @return The new {@link Attachment} <code> id </code>.
     */
    public abstract int addImageAttachment(int imageId, String name);

    /**
     * Deletes an {@link Attachment}
     * 
     * @param attachedFileId
     *            <code>id</code> of {@link Attachment}
     * @return <code>true</code> if the {@link Attachment} was successfully
     *         created. <code>false</code> otherwise.
     */
    public abstract boolean deleteAttachment(int attachedFileId);

    /**
     * Retrieves an {@link ImageAttachment}.
     * 
     * @param attachmentId
     *            <code>id</code> of the {@link ImageAttachment} to be retrieved
     * @return an {@link ImageAttachment}
     */
    public abstract ImageAttachment getAttachment(int attachmentId);

    /**
     * Get the list of {@link ImageAttachment} associated with the {@link Image}
     * of the given <code>id</code>
     * 
     * @param imageId
     *            <code>id</code> of the image.
     * @return list of associated {@link ImageAttachment}.
     */
    public abstract List<ImageAttachment> getImageAttachmentList(int imageId);

}
