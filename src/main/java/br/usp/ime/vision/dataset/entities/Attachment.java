package br.usp.ime.vision.dataset.entities;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.util.AttachmentUtils;

/**
 * Attached file to an entity
 * 
 * @author RafaelLopes
 * 
 */
public class Attachment implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -7427105306500836735L;
    private static Logger logger = LoggerFactory.getLogger(Attachment.class);

    private int id;

    private Date dateCreation;

    private String name;

    /**
     * Removes this {@link Attachment} from system.
     */
    protected void delete_file() {
        AttachmentUtils.deleteAttachment(getId());
    }

    /**
     * Deletes this {@link Attachment} from its parent linked element.
     * 
     * @return <code>true</code> if the {@link Attachment} deletion was
     *         successful
     */
    public boolean deleteAttachedFile() {
        final boolean success = DAOFactory.getAttachmentDAO().deleteAttachment(
                getId());
        if (success) {
            delete_file();
        }

        return success;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Attachment other = (Attachment) obj;
        if (dateCreation == null) {
            if (other.dateCreation != null) {
                return false;
            }
        } else if (!dateCreation.equals(other.dateCreation)) {
            return false;
        }
        if (name == null) {
            if (other.name != null) {
                return false;
            }
        } else if (!name.equals(other.name)) {
            return false;
        }
        return true;
    }

    /**
     * @return the dateCreation
     */
    public Date getDateCreation() {
        return dateCreation;
    }

    /**
     * @return Get associated {@link File} copy.
     */
    public File getFile() {
        File f = null;
        try {
            f = AttachmentUtils.getFile(getId(), getName());
        } catch (final IOException e) {
            logger.error("IoError {}", e);
        }
        return f;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result)
                + ((dateCreation == null) ? 0 : dateCreation.hashCode());
        result = (prime * result) + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    /**
     * @param dateCreation
     *            the dateCreation to set
     */
    public void setDateCreation(final Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    /**
     * @param id
     *            the id to set
     */
    public void setId(final int id) {
        this.id = id;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Attachment [id=" + id + ", dateCreation=" + dateCreation
                + ", name=" + name + "]";
    }

}
