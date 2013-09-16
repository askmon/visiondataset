package br.usp.ime.vision.dataset.entities;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Date;
import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.util.Configs;
import br.usp.ime.vision.dataset.util.ImageUtils;
import br.usp.ime.vision.dataset.util.MimeTypeUtils;

/**
 * Represents an {@link Image} associated to an {@link Album}.
 * 
 * @author Bruno Klava and RafaelLopes
 */
public class Image {

    private int id;

    private String type;

    private int albumId;

    private int ownerId;

    private Date dateUpload;

    private Album album;

    private List<Tag> tags;

    private List<ImageAnnotation> annotations;

    private List<ImageAttachment> attachments;

    /**
     * Deletes this {@link Image} from its parent {@link Album} and removes the
     * image and the thumbnail from the filesystem.
     * 
     * @return <code>true</code> if the {@link Image} deletion was successful
     */
    public boolean deleteImage() {
        final boolean success = DAOFactory.getAlbumDao().deleteImage(getId(),
                getAlbumId());
        if (success) {
            ImageUtils.removeImageAndThumbnail(getId());
        }
        return success;
    }

    public Album getAlbum() {
        if (album == null) {
            album = DAOFactory.getAlbumDao().getAlbum(getAlbumId());
        }
        return album;
    }

    public int getAlbumId() {
        return albumId;
    }

    public List<ImageAnnotation> getAnnotations() {
        if (annotations == null) {
            annotations = DAOFactory.getTagDao().getImageAnnotations(getId());
        }
        return annotations;
    }

    public List<ImageAttachment> getAttachments() {
        if (attachments == null) {
            attachments = DAOFactory.getAttachmentDAO().getImageAttachmentList(
                    getId());
        }
        return attachments;
    }

    public Date getDateUpload() {
        return dateUpload;
    }

    public int getId() {
        return id;
    }

    /**
     * Returns the <code>URL</code> that can be used to access this
     * {@link Image} page.
     * 
     * @return the <code>URL</code> that can be used to access this
     *         {@link Image} page
     */
    public String getImageDetailURL() {
        return "ImageDetail?imageId=" + getId();
    }

    /**
     * Returns the <code>URL</code> that can be used to access this
     * {@link Image}.
     * 
     * @return the <code>URL</code> that can be used to access this
     *         {@link Image}
     */
    public String getImageURL() {
        return "images" + File.separator + getId()
                + MimeTypeUtils.getExtension(getType());
    }

    public int getOwnerId() {
        return ownerId;
    }

    public List<Tag> getTags() {
        if (tags == null) {
            tags = DAOFactory.getTagDao().getImageTags(getId());
        }
        return tags;
    }

    /**
     * Returns the <code>URL</code> that can be used to access this
     * {@link Image} thumbnail.
     * 
     * @return the <code>URL</code> that can be used to access this
     *         {@link Image} thumbnail
     */
    public String getThumbnailURL() {
        return "thumbnails" + File.separator + getId()
                + MimeTypeUtils.getExtension(getType());
    }

    public String getType() {
        return type;
    }

    public URI getUri() {
        final String uri_string = String.format("%sws/image/%d",
                Configs.getServerURL(), getId());
        URI uri;
        try {
            uri = new URI(uri_string);
        } catch (final URISyntaxException e) {
            return null;
        }
        return uri;

    }

    public void setAlbumId(final int albumId) {
        this.albumId = albumId;
    }

    public void setDateUpload(final Date dateUpload) {
        this.dateUpload = dateUpload;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setOwnerId(final int ownerId) {
        this.ownerId = ownerId;
    }

    public void setType(final String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "Image [id=" + id + ", type=" + type + ", albumId=" + albumId
                + ", ownerId=" + ownerId + ", dateUpload=" + dateUpload + "]";
    }

}
