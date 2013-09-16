package br.usp.ime.vision.dataset.dao.interfaces;

import java.util.List;

import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.SubAlbum;
import br.usp.ime.vision.dataset.entities.User;

/**
 * Interface for access to {@link Album} related data.
 * 
 * @author Bruno Klava
 */
public interface AlbumDAO {

    /**
     * Adds an {@link Image} to an {@link Album}.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param ownerId
     *            the <code>id</code> of an {@link User}
     * @param type
     *            the <code>contentType</code> of the {@link Image}
     * @return the <code>id</code> of the new {@link Image}
     */
    public int addImageToAlbum(int albumId, int ownerId, String type);

    /**
     * Adds restricted permission on the {@link Album} of the given
     * <code>albumId</code> to the {@link User} with the given
     * <code>userId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param userId
     *            the <code>id</code> of an {@link User}
     * @return <code>true</code> if the permission was add successfully
     */
    public boolean addRestrictedPermission(int albumId, int userId);

    /**
     * Sets the {@link User} with the given <code>userId</code> as owner of the
     * {@link Album} of the given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param userId
     *            the <code>id</code> of an {@link User}
     * @return <code>true</code> if the owner change was successful
     */
    public boolean changeAlbumOwner(int albumId, int userId);

    /**
     * Adds the <code>album</code> in the system.
     * 
     * @param album
     *            a new {@link Album}
     * @return the <code>id</code> of the {@link Album} created
     */
    public int createAlbum(Album album);

    /**
     * Deletes an {@link Album}.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return <code>true</code> if the {@link Album} deletion was successful
     */
    public boolean deleteAlbum(int albumId);

    /**
     * Deletes an {@link Image} from an {@link Album}.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return <code>true</code> if the {@link Image} deletion was successful
     */
    public boolean deleteImage(int imageId, int albumId);

    /**
     * Returns the {@link Album} with the given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return the {@link Album} with the given <code>albumId</code>
     */
    public Album getAlbum(int albumId);

    /**
     * Returns the {@link Album} which contains the {@link Image} with the given
     * <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @return the {@link Album} which contains the {@link Image} with the given
     *         <code>imageId</code>
     */
    public Album getAlbumForImage(int imageId);

    /**
     * Returns a <code>List</code> with the {@link Album}s that the {@link User}
     * with the given <code>userId</code> has permissions. It excludes the
     * {@link SubAlbum}s.
     * 
     * @param userId
     *            the id of a {@link User}
     * @return a <code>List</code> with the {@link Album}s that the {@link User}
     *         with the given <code>userId</code> has permissions
     */
    public List<Album> getAlbumsUser(int userId);

    /**
     * Returns a <code>List</code> with the {@link Album}s that the {@link User}
     * with the given <code>userId</code> has create permission. It excludes the
     * {@link SubAlbum}s.
     * 
     * @param userId
     *            the id of a {@link User}
     * @return a <code>List</code> with the {@link Album}s that the {@link User}
     *         with the given <code>userId</code> has create permission
     */
    public List<Album> getAlbumsUserCreate(int userId);

    /**
     * Returns a <code>List</code> with all the {@link Album}s except for the
     * {@link SubAlbum}s.
     * 
     * @return a <code>List</code> with all the {@link Album}s except for the
     *         {@link SubAlbum}s
     */
    public List<Album> getAllAlbums();

    /**
     * Returns the {@link Image} of the given <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @return the {@link Image} of the given <code>imageId</code>
     */
    public Image getImage(int imageId);

    /**
     * Returns a <code>List</code> of the {@link Image}s of the {@link Album} of
     * the given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return a <code>List</code> of the {@link Image}s of the {@link Album} of
     *         the given <code>albumId</code>
     */
    public List<Image> getImages(int albumId);

    /**
     * Returns a <code>List</code> with the {@link Album}s with public view
     * permission. It excludes the {@link SubAlbum}s.
     * 
     * @return a <code>List</code> with the {@link Album}s with public view
     *         permission
     */
    public List<Album> getPublicAlbums();

    /**
     * Returns a <code>List</code> of the {@link SubAlbum}s of the {@link Album}
     * of the given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return a <code>List</code> of the {@link SubAlbum}s of the {@link Album}
     *         of the given <code>albumId</code>
     */
    public List<Album> getSubAlbums(int albumId);

    /**
     * Returns a <code>List</code> of the {@link User}s that don't have
     * additional permissions on the {@link Album} of the given
     * <code>albumId</code> (it excludes the owner of the {@link Album}).
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return a <code>List</code> of the {@link User}s that have additional
     *         permissions on the {@link Album} of the given
     *         <code>albumId</code>
     */
    public List<User> getUsersWithoutPermission(int albumId);

    /**
     * Returns a <code>List</code> of the {@link User}s that have additional
     * permissions on the {@link Album} of the given <code>albumId</code>,
     * excluding the owner of the {@link Album}.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return a <code>List</code> of the {@link User}s that have additional
     *         permissions on the {@link Album} of the given
     *         <code>albumId</code>
     */
    public List<User> getUsersWithPermission(int albumId);

    /**
     * Moves the {@link Image} of the given <code>imageId</code> from the
     * {@link Album} with id <code>albumSrcId</code> to the {@link Album} with
     * id <code>albumDstId</code>.
     * 
     * @param albumSrcId
     *            the <code>id</code> of an {@link Album}
     * @param albumDstId
     *            the <code>id</code> of an {@link Album}
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @return <code>true</code> if the image was moved successfully
     */
    public boolean moveImage(int albumSrcId, int albumDstId, int imageId);

    /**
     * Moves the {@link Album} of the given <code>albumId</code> from the
     * {@link Album} with id <code>albumSrcId</code> to the {@link Album} with
     * id <code>albumDstId</code>.
     * 
     * @param albumSrcId
     *            the <code>id</code> of an {@link Album}
     * @param albumDstId
     *            the <code>id</code> of an {@link Album}
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return <code>true</code> if the image was moved successfully
     */
    public boolean moveSubAlbum(int albumSrcId, int albumDstId, int albumId);

    /**
     * Renames the {@link Album} of the given <code>albumId</code> to
     * <code>albumName</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param albumName
     *            the new <code>name</code> for the {@link Album}
     * @return <code>true</code> if the {@link Album} renaming was successful
     */
    public boolean renameAlbum(int albumId, String albumName);

    /**
     * Sets the permissions of the {@link Album} of the given
     * <code>albumId</code>. It removes all the restricted permissions.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param viewPermission
     *            the new <code>viewPermission</code> of the {@link Album}
     * @param createPermission
     *            the new <code>createPermission</code> of the {@link Album}
     * @return <code>true</code> if the {@link Album} permissions were set
     *         successfully
     */
    public boolean setAlbumPermissions(int albumId, int viewPermission,
            int createPermission);

    /**
     * Checks if the {@link User} with the given <code>userId</code> has
     * restricted permission on the {@link Album} of the given
     * <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param userId
     *            the <code>id</code> of an {@link User}
     * @return <code>true</code> if the permission exists
     */
    public boolean userHasPermissionOnAlbum(int albumId, int userId);

}
