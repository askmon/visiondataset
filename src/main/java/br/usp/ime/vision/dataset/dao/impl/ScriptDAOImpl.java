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

import br.usp.ime.vision.dataset.dao.interfaces.ScriptDAO;
import br.usp.ime.vision.dataset.entities.ImageScript;

/**
 * @author ask
 * 
 */
@SuppressWarnings("rawtypes")
public class ScriptDAOImpl extends DAOImpl implements ScriptDAO {
    private static Logger logger = LoggerFactory
            .getLogger(ScriptDAOImpl.class);

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#addImageAttachedFile
     * (int, java.lang.String, java.lang.String)
     */

    public int addScript(final int imageId, final String name) {
        int id = 0;
        logger.debug("Adding ImageScriptFile {} from image id {}", name,
                imageId);
        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addImageScript\"( ? , ?, ?)}");
            cs.setInt(1, imageId);
            cs.setString(2, name);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info(
                        "Added ImageScriptFile {} to image {} with id {}",
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

    /*public boolean deleteAttachment(final int attachmentId) {
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
    }*/

    /*
     * (non-Javadoc)
     * 
     * @see
     * br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#getImageAttachedFile
     * (int)
     */

    public ImageScript getScript(final int scriptFileId) {
        ImageScript imageScript = null;
        logger.debug("Getting script with id {}", scriptFileId);
        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageScriptFile\"( ? )}");

            cs.setInt(1, scriptFileId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                imageScript = getImageScriptFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return imageScript;
    }

    /**
     * @see br.usp.ime.vision.dataset.dao.interfaces.AttachedFileDAO#getImageAttachedFileList(int)
     */

    public List<ImageScript> getImageScriptList(final int imageId) {
        final List<ImageScript> scripts = new ArrayList<ImageScript>();
        logger.debug("Getting annotations of image id {}", imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageScripts\"( ? )}");

            cs.setInt(1, imageId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                scripts.add(getImageScriptFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return scripts;
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
    private ImageScript getImageScriptFromDB(final ResultSet rs,
            final Object row) {
        ImageScript attachedFile = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                attachedFile = new ImageScript();
                BeanUtils.copyProperties(attachedFile, bean);
                attachedFile.setImageId(rs.getInt("imageId"));
                attachedFile.setDateCreation(rs.getTimestamp("dateCreation"));
                logger.debug("Found script file: {}", attachedFile);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return attachedFile;
    }

}
