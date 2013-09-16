/**
 * 
 */
package br.usp.ime.vision.dataset.dao.impl;

import java.sql.CallableStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.DynaBean;
import org.apache.commons.beanutils.ResultSetDynaClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.interfaces.AttachmentDAO;
import br.usp.ime.vision.dataset.entities.ImageAttachment;

/**
 * @author RafaelLopes
 * 
 */
@SuppressWarnings("rawtypes")
public class AttachmentDAOImpl extends DAOImpl implements AttachmentDAO {
    private static Logger logger = LoggerFactory
            .getLogger(AttachmentDAOImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#addImageAttachedFile
     * (int, java.lang.String, java.lang.String)
     */

    public int addImageAttachment(final int imageId, final String name) {
        int id = 0;
        logger.debug("Adding ImageAttachedFile {} from image id {}", name,
                imageId);
        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addImageAttachment\"( ? , ? , ? )}");
            cs.setInt(1, imageId);
            cs.setString(2, name);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info(
                        "Added ImageAttachedFile {} to image {} with id {}",
                        new Object[] { name, imageId, id });
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return id;

    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#deleteAttachedFile
     * (int)
     */

    public boolean deleteAttachment(final int attachmentId) {
        logger.debug("Deleting Attachment {}", attachmentId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"deleteImageAttachment\"( ? )}");

            cs.setInt(1, attachmentId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug("Attachment removed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#getImageAttachedFile
     * (int)
     */

    public ImageAttachment getAttachment(final int attachedFileId) {
        ImageAttachment imageAttachment = null;
        logger.debug("Getting attachment with id {}", attachedFileId);
        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageAttachedFile\"( ? )}");

            cs.setInt(1, attachedFileId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                imageAttachment = getImageTagFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return imageAttachment;
    }

    /**
     * @see br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#getImageAttachedFileList(int)
     */

    public List<ImageAttachment> getImageAttachmentList(final int imageId) {
        final List<ImageAttachment> attachments = new ArrayList<ImageAttachment>();
        logger.debug("Getting annotations of image id {}", imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageAttachments\"( ? )}");

            cs.setInt(1, imageId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                attachments.add(getImageTagFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return attachments;
    }

    /**
     * Returns an {@link ImageAttachment} with the data provided by the
     * <code>rs</code> .
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link ImageAttachment} from the database
     */
    private ImageAttachment getImageTagFromDB(final ResultSet rs,
            final Object row) {
        ImageAttachment attachedFile = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                attachedFile = new ImageAttachment();
                BeanUtils.copyProperties(attachedFile, bean);
                attachedFile.setImageId(rs.getInt("imageId"));
                attachedFile.setDateCreation(rs.getTimestamp("dateCreation"));
                logger.debug("Found attached file: {}", attachedFile);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return attachedFile;
    }

}
