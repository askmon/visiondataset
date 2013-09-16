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

import br.usp.ime.vision.dataset.dao.interfaces.AlbumDAO;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.SubAlbum;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Implementation of the {@link AlbumDAO} interface.
 * 
 * @author Bruno Klava
 */
@SuppressWarnings("rawtypes")
public class AlbumDAOImpl extends DAOImpl implements AlbumDAO {

    private static Logger logger = LoggerFactory.getLogger(AlbumDAOImpl.class);

    public int addImageToAlbum(final int albumId, final int ownerId,
            final String type) {

        int id = 0;

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addImageToAlbum\"( ? , ? , ? , ? )}");

            cs.setString(1, type);
            cs.setInt(2, albumId);
            cs.setInt(3, ownerId);
            cs.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info("Image id {} added to album id {} owner id {}",
                        new Object[] { id, albumId, ownerId });
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return id;
    }

    public boolean addRestrictedPermission(final int albumId, final int userId) {
        logger.debug(
                "Adding restricted permission on album id {} to user id {}",
                albumId, userId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"addAlbumRestrictedPermission\"( ? , ? )}");

            cs.setInt(1, albumId);
            cs.setInt(2, userId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info(
                        "Restricted permission on album id {} added to user id {}",
                        albumId, userId);
            }
            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean changeAlbumOwner(final int albumId, final int userId) {

        logger.debug("Setting user id {} as owner of album id {}", userId,
                albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"changeAlbumOwner\"( ? , ? , ? )}");

            cs.setInt(1, albumId);
            cs.setInt(2, userId);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug("User id {} set as owner of   album id {}",
                        userId, albumId);
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public int createAlbum(final Album album) {

        int id = 0;

        logger.debug("Creating new album");

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"createAlbum\"( ? , ? , ? , ? , ? , ? )}");

            cs.setString(1, album.getName());
            cs.setInt(2, album.getOwnerId());
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            cs.setInt(4, album.getViewPermission());
            cs.setInt(5, album.getCreatePermission());
            int parentId = 0;
            if (album instanceof SubAlbum) {
                parentId = ((SubAlbum) album).getParentId();
            }
            cs.setInt(6, parentId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                id = rs.getInt("result");
                logger.info("Created album with id {} ownerId {}", id,
                        album.getOwnerId());
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return id;
    }

    public boolean deleteAlbum(final int albumId) {

        logger.debug("Deleting album {}", albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"deleteAlbum\"( ? )}");

            cs.setInt(1, albumId);

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Album {} deleted!", albumId);
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean deleteImage(final int imageId, final int albumId) {

        logger.debug("Deleting image {} from album {}", imageId, albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"deleteImage\"( ? , ? , ? )}");

            cs.setInt(1, imageId);
            cs.setInt(2, albumId);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Image removed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public Album getAlbum(final int albumId) {

        Album album = null;

        logger.debug("Getting album of id {}", albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAlbum\"( ? )}");

            cs.setInt(1, albumId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                album = getAlbumFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return album;

    }

    public Album getAlbumForImage(final int imageId) {

        Album album = null;

        logger.debug("Getting album of image id {}", imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAlbumForImage\"( ? )}");

            cs.setInt(1, imageId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                album = getAlbumFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return album;

    }

    /**
     * Returns an {@link Album} with the data provided by the <code>rs</code> .
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link Album} from the database
     */
    private Album getAlbumFromDB(final ResultSet rs, final Object row) {
        Album album = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {

                if (rs.getInt("parentId") == 0) {
                    album = new Album();
                    album.setViewPermission(rs.getInt("viewPermission"));
                    album.setCreatePermission(rs.getInt("createPermission"));
                } else {
                    album = new SubAlbum();
                    ((SubAlbum) album).setParentId(rs.getInt("parentId"));
                }

                BeanUtils.copyProperties(album, bean);
                // XXX BeanUtils bug with cammelCase?
                album.setOwnerId(rs.getInt("ownerId"));
                album.setDateCreation(rs.getTimestamp("dateCreation"));
                album.setDateUpdate(rs.getTimestamp("dateUpdate"));
                logger.debug("Found album: {}", album);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return album;
    }

    public List<Album> getAlbumsUser(final int userId) {

        final List<Album> albums = new ArrayList<Album>();
        logger.debug("Getting albums for user id {}", userId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAlbumsUser\"( ? )}");

            cs.setInt(1, userId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                albums.add(getAlbumFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return albums;

    }

    public List<Album> getAlbumsUserCreate(final int userId) {

        final List<Album> albums = new ArrayList<Album>();
        logger.debug("Getting albums for user id {} with create permission",
                userId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAlbumsUserCreate\"( ? )}");

            cs.setInt(1, userId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                albums.add(getAlbumFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return albums;

    }

    public List<Album> getAllAlbums() {

        final List<Album> albums = new ArrayList<Album>();
        logger.debug("Getting all (root) albums");

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getAllAlbums\"( )}");

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                albums.add(getAlbumFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return albums;

    }

    public Image getImage(final int imageId) {

        Image image = null;

        logger.debug("Getting image id {}", imageId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImage\"( ? )}");

            cs.setInt(1, imageId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            if (rows.hasNext()) {
                image = getImageFromDB(rs, rows.next());
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return image;
    }

    /**
     * Returns an {@link Image} with the data provided by the <code>rs</code> .
     * 
     * @param rs
     *            an <code>ResultSet</code> from a query made to the database
     * @param row
     *            a <code>DynaBean</code> that encapsulates an row from the
     *            <code>rs</code>
     * @return an {@link Image} from the database
     */
    private Image getImageFromDB(final ResultSet rs, final Object row) {
        Image image = null;
        try {
            final DynaBean bean = (DynaBean) row;
            if (bean.get("id") != null) {
                image = new Image();
                BeanUtils.copyProperties(image, bean);
                // XXX BeanUtils bug with cammelCase?
                image.setAlbumId(rs.getInt("albumId"));
                image.setOwnerId(rs.getInt("ownerId"));
                image.setDateUpload(rs.getTimestamp("dateUpload"));
                logger.debug("Found image: {}", image);
            }
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return image;
    }

    public List<Image> getImages(final int albumId) {

        final List<Image> images = new ArrayList<Image>();
        logger.debug("Getting images of album id {}", albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getImagesAlbum\"( ? )}");

            cs.setInt(1, albumId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                images.add(getImageFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return images;
    }

    public List<Album> getPublicAlbums() {

        final List<Album> albums = new ArrayList<Album>();
        logger.debug("Getting public albums");

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getPublicAlbums\"( )}");

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                albums.add(getAlbumFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return albums;

    }

    public List<Album> getSubAlbums(final int albumId) {

        final List<Album> albums = new ArrayList<Album>();
        logger.debug("Getting sub albums of album id {}", albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getSubAlbums\"( ? )}");

            cs.setInt(1, albumId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                albums.add(getAlbumFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return albums;
    }

    public List<User> getUsersWithoutPermission(final int albumId) {

        final List<User> users = new ArrayList<User>();
        logger.debug(
                "Getting users without additional permissions on album id {}",
                albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getUsersWithoutPermission\"( ? )}");

            cs.setInt(1, albumId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                users.add(getUserFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return users;
    }

    public List<User> getUsersWithPermission(final int albumId) {

        final List<User> users = new ArrayList<User>();
        logger.debug(
                "Getting users with additional permissions on album id {}",
                albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"getUsersWithPermission\"( ? )}");

            cs.setInt(1, albumId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            final ResultSetDynaClass rsdc = new ResultSetDynaClass(rs);
            final Iterator rows = rsdc.iterator();

            while (rows.hasNext()) {
                users.add(getUserFromDB(rs, rows.next()));
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
        }

        return users;
    }

    public boolean moveImage(final int albumSrcId, final int albumDstId,
            final int imageId) {

        logger.debug("Moving image id {} from album id {} to album id {}",
                new Object[] { imageId, albumSrcId, albumDstId });

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"moveImage\"( ? , ? , ? , ? )}");

            cs.setInt(1, albumSrcId);
            cs.setInt(2, albumDstId);
            cs.setInt(3, imageId);
            cs.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug(
                        "Image id {} moved from album id {} to album id {}",
                        new Object[] { imageId, albumSrcId, albumDstId });
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean moveSubAlbum(final int albumSrcId, final int albumDstId,
            final int albumId) {

        logger.debug("Moving album id {} from album id {} to album id {}",
                new Object[] { albumId, albumSrcId, albumDstId });

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"moveAlbum\"( ? , ? , ? , ? )}");

            cs.setInt(1, albumSrcId);
            cs.setInt(2, albumDstId);
            cs.setInt(3, albumId);
            cs.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.debug(
                        "Album id {} moved from album id {} to album id {}",
                        new Object[] { albumId, albumSrcId, albumDstId });
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean renameAlbum(final int albumId, final String albumName) {
        logger.debug("Renaming album id {} to  {}", albumId, albumName);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"renameAlbum\"( ? , ? , ? )}");

            cs.setInt(1, albumId);
            cs.setString(2, albumName);
            cs.setTimestamp(3, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Album renamed!");
            }

            cs.close();
            closeConnection();
        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean setAlbumPermissions(final int albumId,
            final int viewPermission, final int createPermission) {
        logger.debug("Setting permissions of album id {}", albumId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"setAlbumPermissions\"( ? , ? , ? , ? )}");

            cs.setInt(1, albumId);
            cs.setInt(2, viewPermission);
            cs.setInt(3, createPermission);
            cs.setTimestamp(4, new Timestamp(System.currentTimeMillis()));

            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                logger.info("Permissions set successfully!");
            }

            cs.close();
            closeConnection();
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        return true;
    }

    public boolean userHasPermissionOnAlbum(final int albumId, final int userId) {

        boolean permission = false;

        logger.debug(
                "Checking restricted permission on album id {} of user id {}",
                albumId, userId);

        try {

            final CallableStatement cs = getConnection().prepareCall(
                    "{call \"userHasPermissionOnAlbum\"( ? , ? )}");

            cs.setInt(1, albumId);
            cs.setInt(2, userId);
            cs.executeUpdate();
            final ResultSet rs = cs.getResultSet();

            if (rs.next()) {
                permission = rs.getBoolean(1);
            }

            cs.close();
            closeConnection();

        } catch (final SQLException e) {
            logger.error(e.getMessage(), e);
            return false;
        }

        if (permission) {
            logger.debug("User id {} has restricted permission on album id {}",
                    userId, albumId);
        } else {
            logger.debug(
                    "User id {} doesn't have restricted permission on album id {}",
                    userId, albumId);
        }

        return permission;
    }
}
