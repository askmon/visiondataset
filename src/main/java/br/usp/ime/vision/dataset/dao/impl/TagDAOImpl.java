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

import br.usp.ime.vision.dataset.dao.interfaces.TagDAO;
import br.usp.ime.vision.dataset.entities.AlbumTag;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;
import br.usp.ime.vision.dataset.entities.ImageTag;
import br.usp.ime.vision.dataset.entities.Tag;

/**
 * Implementation of the {@link TagDAO} interface.
 * 
 * @author Bruno Klava
 */
@SuppressWarnings("rawtypes")
public class TagDAOImpl extends DAOImpl implements TagDAO {

    private static Logger logger = LoggerFactory.getLogger(TagDAOImpl.class);

    public int addAlbumTag(final int albumId, final String tagName) {

        int id = 0;

        logger.debug("Adding tag {} to album id {}", tagName, albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addAlbumTag\"( ? , ? , ? )}");

            cs.setString(1, tagName);
            cs.setInt(2, albumId);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info("Added tag {} to album {} with id {}",
                        new Object[] { tagName, albumId, id });
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return id;
    }

    public int addImageAnnotation(final int imageId, final String tagName,
            final int x, final int y, final int width, final int height) {

        int id = 0;

        logger.debug("Adding annotation {} to image id {}", tagName, imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addImageAnnotation\"( ? , ? , ? , ? , ? , ? )}");

            cs.setString(1, tagName);
            cs.setInt(2, imageId);
            cs.setInt(3, x);
            cs.setInt(4, y);
            cs.setInt(5, width);
            cs.setInt(6, height);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info("Added annotation {} to image {} with id {}",
                        new Object[] { tagName, imageId, id });
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return id;
    }

    public int addImageTag(final int imageId, final String tagName) {

        int id = 0;

        logger.debug("Adding tag {} to image id {}", tagName, imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addImageTag\"( ? , ? )}");

            cs.setString(1, tagName);
            cs.setInt(2, imageId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info("Added tag {} to image {} with id {}",
                        new Object[] { tagName, imageId, id });
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return id;
    }

    public boolean deleteAlbumTag(final int albumId, final int tagId) {

        logger.debug("Deleting tag {} from album {}", tagId, albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"deleteAlbumTag\"( ? , ? , ? )}");

            cs.setInt(1, tagId);
            cs.setInt(2, albumId);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug("Tag removed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public boolean deleteImageAnnotation(final int imageId, final int tagId) {

        logger.debug("Deleting annotation {} from image {}", tagId, imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"deleteImageAnnotation\"( ? , ? )}");

            cs.setInt(1, tagId);
            cs.setInt(2, imageId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug("Annotation removed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public boolean deleteImageTag(final int imageId, final int tagId) {

        logger.debug("Deleting tag {} from image {}", tagId, imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"deleteImageTag\"( ? , ? )}");

            cs.setInt(1, tagId);
            cs.setInt(2, imageId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug("Tag removed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;

    }

    public AlbumTag getAlbumTag(final int albumId, final int tagId) {

        AlbumTag tag = null;

        logger.debug("Getting tag id {} of album id {}", tagId, albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAlbumTag\"( ? , ? )}");

            cs.setInt(1, albumId);
            cs.setInt(2, tagId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                tag = getAlbumTagFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return tag;

    }

    /**
     * Returns an {@link AlbumTag} with the data provided by the <code>rs</code>
     * .
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link AlbumTag} from the database
     */
    private AlbumTag getAlbumTagFromDB(final ResultSet rs, final Object row) {
        AlbumTag tag = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                tag = new AlbumTag();
                BeanUtils.copyProperties(tag, bean);
                // XXX reserved word?
                tag.setTagName(rs.getString("name"));
                // XXX BeanUtils bug with cammelCase?
                tag.setAlbumId(rs.getInt("albumId"));
                logger.debug("Found tag: {}", tag);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return tag;
    }

    public List<Tag> getAlbumTags(final int albumId) {

        final List<Tag> tags = new ArrayList<Tag>();
        logger.debug("Getting tags of album id {}", albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAlbumTags\"( ? )}");

            cs.setInt(1, albumId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                tags.add(getAlbumTagFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return tags;
    }

    /**
     * Returns an {@link ImageAnnotation} with the data provided by the
     * <code>rs</code> .
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link ImageAnnotation} from the database
     */
    private ImageAnnotation getAnnotationFromDB(final ResultSet rs,
            final Object row) {
        ImageAnnotation annotation = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                annotation = new ImageAnnotation();
                BeanUtils.copyProperties(annotation, bean);
                // XXX reserved word?
                annotation.setTagName(rs.getString("name"));
                // XXX BeanUtils bug with cammelCase?
                annotation.setImageId(rs.getInt("imageId"));
                logger.debug("Found annotation: {}", annotation);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return annotation;
    }

    public ImageAnnotation getImageAnnotation(final int imageId, final int tagId) {

        ImageAnnotation annotation = null;

        logger.debug("Getting annotation id {} of image id {}", tagId, imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageAnnotation\"( ? , ? )}");

            cs.setInt(1, imageId);
            cs.setInt(2, tagId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                annotation = getAnnotationFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return annotation;

    }

    public List<ImageAnnotation> getImageAnnotations(final int imageId) {

        final List<ImageAnnotation> annotations = new ArrayList<ImageAnnotation>();
        logger.debug("Getting annotations of image id {}", imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageAnnotations\"( ? )}");

            cs.setInt(1, imageId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                annotations.add(getAnnotationFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return annotations;

    }

    public ImageTag getImageTag(final int imageId, final int tagId) {

        ImageTag tag = null;

        logger.debug("Getting tag id {} of image id {}", tagId, imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageTag\"( ? , ? )}");

            cs.setInt(1, imageId);
            cs.setInt(2, tagId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                tag = getImageTagFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return tag;

    }

    /**
     * Returns an {@link ImageTag} with the data provided by the <code>rs</code>
     * .
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link ImageTag} from the database
     */
    private ImageTag getImageTagFromDB(final ResultSet rs, final Object row) {
        ImageTag tag = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                tag = new ImageTag();
                BeanUtils.copyProperties(tag, bean);
                // XXX reserved word?
                tag.setTagName(rs.getString("name"));
                // XXX BeanUtils bug with cammelCase?
                tag.setImageId(rs.getInt("imageId"));
                logger.debug("Found tag: {}", tag);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return tag;
    }

    public List<Tag> getImageTags(final int imageId) {

        final List<Tag> tags = new ArrayList<Tag>();
        logger.debug("Getting tags of image id {}", imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImageTags\"( ? )}");

            cs.setInt(1, imageId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                tags.add(getImageTagFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return tags;
    }

    public List<String> getTagsNames() {

        final List<String> tagsNames = new ArrayList<String>();
        String tagName;

        logger.debug("Getting all tags names");

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getTagsNames\"( )}");

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            while (rs.next()) {
                tagName = rs.getString("result");
                tagsNames.add(tagName);
                logger.debug("Found tag name {}", tagName);
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return tagsNames;

    }

    public boolean updateAnnotation(final int tagId, final String tagName,
            final int x, final int y, final int width, final int height) {

        logger.debug("Updating annotation {}", tagId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"updateAnnotation\"( ? , ? , ? , ? , ? , ? )}");

            cs.setInt(1, tagId);
            cs.setString(2, tagName);
            cs.setInt(3, x);
            cs.setInt(4, y);
            cs.setInt(5, width);
            cs.setInt(6, height);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug("Annotation updated!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

}
