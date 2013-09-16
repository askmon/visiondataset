package br.usp.ime.vision.dataset.entities;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.util.Constants;
import br.usp.ime.vision.dataset.util.Messages;

/**
 * Represents an {@link Album} that can contain {@link Image}s and other
 * {@link Album}s.
 * 
 * @author Bruno Klava
 */
public class Album {

    /**
     * Returns the <code>String</code> representation of an {@link Album}
     * <code>createPermission</code>.
     * 
     * @return the <code>String</code> representation of an {@link Album}
     *         <code>createPermission</code>
     */
    public static String getCreatePermissionString(final int createPermission) {
        switch (createPermission) {
        case Constants.ALBUM_USERS_CREATE:
            return Messages.getMessage("album.permission.create.users");
        default:
        case Constants.ALBUM_PRIVATE_CREATE:
            return Messages.getMessage("album.permission.create.private");
        }
    }

    /**
     * Returns the <code>String</code> representation of an {@link Album}
     * <code>viewPermission</code>.
     * 
     * @return the <code>String</code> representation of an {@link Album}
     *         <code>viewPermission</code>
     */
    public static String getViewPermissionString(final int viewPermission) {
        switch (viewPermission) {
        case Constants.ALBUM_PUBLIC_VIEW:
            return Messages.getMessage("album.permission.view.public");
        case Constants.ALBUM_USERS_VIEW:
            return Messages.getMessage("album.permission.view.users");
        default:
        case Constants.ALBUM_PRIVATE_VIEW:
            return Messages.getMessage("album.permission.view.private");
        }
    }

    private int id;

    private String name;

    private int ownerId;

    private Date dateCreation;

    private Date dateUpdate;

    private List<Image> images;

    private int viewPermission;

    private int createPermission;

    private List<Album> subAlbums;

    protected List<Album> parents;

    private List<Album> albumsChain;

    private List<Tag> tags;

    /**
     * Deletes this {@link Album} and all the {@link Album}s and {@link Image}s
     * under this {@link Album} hierarchy
     * 
     * @return <code>true</code> if the {@Album} deletion was successful
     */
    public boolean deleteAlbum() {

        for (final Album subAlbum : getSubAlbums()) {
            if (!subAlbum.deleteAlbum()) {
                return false;
            }
        }

        for (final Image image : getImages()) {
            if (!image.deleteImage()) {
                return false;
            }
        }

        return DAOFactory.getAlbumDao().deleteAlbum(getId());

    }

    /**
     * Returns a <code>List</code> with all the {@link Album}s from the
     * hierarchy root to this {@link Album}.
     * 
     * @return the parents of this {@link Album}
     */
    public List<Album> getAlbumsChain() {
        if (albumsChain == null) {
            albumsChain = new ArrayList<Album>();
            albumsChain.addAll(getParents());
            albumsChain.add(this);
        }
        return albumsChain;
    }

    public int getCreatePermission() {
        return createPermission;
    }

    /**
     * Returns the <code>String</code> representation of this {@link Album}
     * <code>createPermission</code>.
     * 
     * @return the <code>String</code> representation of this {@link Album}
     *         <code>createPermission</code>
     */
    public String getCreatePermissionString() {
        return getCreatePermissionString(createPermission);
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public Date getDateUpdate() {
        return dateUpdate;
    }

    public int getId() {
        return id;
    }

    public List<Image> getImages() {
        if (images == null) {
            images = DAOFactory.getAlbumDao().getImages(getId());
        }
        return images;
    }

    public String getName() {
        return name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    /**
     * Returns a <code>List</code> with the parent {@link Album}s of this
     * {@link Album}.
     * 
     * @return the parents of this {@link Album}
     */
    public List<Album> getParents() {
        if (parents == null) {
            parents = new ArrayList<Album>();
        }
        return parents;
    }

    /**
     * Returns the {@link Album}s that is the root of the hierarchy that this
     * {@link Album} belongs to.
     * 
     * @return the hierarchy root of this {@link Album}
     */
    public Album getRootAlbum() {
        return getAlbumsChain().get(0);
    }

    /**
     * Returns the {@link Album}s that is the root of the hierarchy that this
     * {@link Album} belongs to.
     * 
     * @return the hierarchy root of this {@link Album}
     */
    public List<Album> getSubAlbums() {
        if (subAlbums == null) {
            subAlbums = DAOFactory.getAlbumDao().getSubAlbums(getId());
        }
        return subAlbums;
    }

    public List<Tag> getTags() {
        if (tags == null) {
            tags = DAOFactory.getTagDao().getAlbumTags(getId());
        }
        return tags;
    }

    public int getViewPermission() {
        return viewPermission;
    }

    /**
     * Returns the <code>String</code> representation of this {@link Album}
     * <code>viewPermission</code>.
     * 
     * @return the <code>String</code> representation of this {@link Album}
     *         <code>viewPermission</code>
     */
    public String getViewPermissionString() {
        return getViewPermissionString(viewPermission);
    }

    /**
     * Returns if this {@link Album} doesn't contain any {@link Album}s nor
     * {@link Image}s.
     * 
     * @return <code>true</code> if this {@link Album} doesn't contain any
     *         {@link Album}s nor {@link Image}s
     */
    public boolean isEmptyAlbum() {
        return getImages().isEmpty() && getSubAlbums().isEmpty();
    }

    /**
     * Returns if this {@link Album} has {@link Constants.ALBUM_PUBLIC_VIEW}
     * <code>viewPermission</code>.
     * 
     * @return <code>true</code> if this {@link Album} has
     *         {@link Constants.ALBUM_PUBLIC_VIEW} <code>viewPermission</code>
     */
    public boolean isPublicView() {
        return getViewPermission() == Constants.ALBUM_PUBLIC_VIEW;
    }

    /**
     * Returns if this {@link Album} has {@link Constants.ALBUM_PRIVATE_CREATE}
     * <code>createPermission</code>.
     * 
     * @return <code>true</code> if this {@link Album} has
     *         {@link Constants.ALBUM_PRIVATE_CREATE}
     *         <code>createPermission</code>
     */
    public boolean isRestrictedCreate() {
        return getCreatePermission() == Constants.ALBUM_PRIVATE_CREATE;
    }

    /**
     * Returns if this {@link Album} has {@link Constants.ALBUM_PRIVATE_VIEW}
     * <code>viewPermission</code>.
     * 
     * @return <code>true</code> if this {@link Album} has
     *         {@link Constants.ALBUM_PRIVATE_VIEW} <code>viewPermission</code>
     */
    public boolean isRestrictedView() {
        return getViewPermission() == Constants.ALBUM_PRIVATE_VIEW;
    }

    /**
     * Returns if this {@link Album} is the root of its hierarchy.
     * 
     * @return <code>true</code> if this {@link Album} is the root of its
     *         hierarchy
     */
    public boolean isRootAlbum() {
        return true;
    }

    /**
     * Returns if this {@link Album} has {@link Constants.ALBUM_USERS_CREATE}
     * <code>createPermission</code>.
     * 
     * @return <code>true</code> if this {@link Album} has
     *         {@link Constants.ALBUM_USERS_CREATE}
     *         <code>createPermission</code>
     */
    public boolean isUsersCreate() {
        return getCreatePermission() == Constants.ALBUM_USERS_CREATE;
    }

    /**
     * Returns if this {@link Album} has {@link Constants.ALBUM_USERS_VIEW}
     * <code>viewPermission</code>.
     * 
     * @return <code>true</code> if this {@link Album} has
     *         {@link Constants.ALBUM_USERS_VIEW} <code>viewPermission</code>
     */
    public boolean isUsersView() {
        return getViewPermission() == Constants.ALBUM_USERS_VIEW;
    }

    public void setCreatePermission(final int createPermission) {
        this.createPermission = createPermission;
    }

    public void setDateCreation(final Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public void setDateUpdate(final Date dateUpdate) {
        this.dateUpdate = dateUpdate;
    }

    public void setId(final int id) {
        this.id = id;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public void setOwnerId(final int ownerId) {
        this.ownerId = ownerId;
    }

    public void setViewPermission(final int viewPermission) {
        this.viewPermission = viewPermission;
    }

    @Override
    public String toString() {
        return "Album [id=" + id + ", name=" + name + ", ownerId=" + ownerId
                + ", dateCreation=" + dateCreation + ", dateUpdate="
                + dateUpdate + ", viewPermission=" + getViewPermissionString()
                + ", createPermission=" + getCreatePermissionString() + "]";
    }

}
