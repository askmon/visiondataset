package br.usp.ime.vision.dataset.entities;

import java.util.List;

import br.usp.ime.vision.dataset.dao.DAOFactory;

/**
 * Represents an {@link SubAlbum} that can contain {@link Image}s and other
 * {@link Album}s an its a child of an {@link Album}. It heritages the
 * permissions from the {@link Album} that is the root of this {@link SubAlbum}
 * hierarchy.
 * 
 * @author Bruno Klava
 */
public class SubAlbum extends Album {

    private int parentId;

    private Album parent;

    public Album getParent() {
        if (parent == null) {
            parent = DAOFactory.getAlbumDao().getAlbum(getParentId());
        }
        return parent;
    }

    public int getParentId() {
        return parentId;
    }

    @Override
    public List<Album> getParents() {
        if (parents == null) {
            parents = getParent().getAlbumsChain();
        }
        return parents;
    }

    @Override
    public boolean isPublicView() {
        return getRootAlbum().isPublicView();
    }

    @Override
    public boolean isRestrictedCreate() {
        return getRootAlbum().isRestrictedCreate();
    }

    @Override
    public boolean isRestrictedView() {
        return getRootAlbum().isRestrictedView();
    }

    @Override
    public boolean isRootAlbum() {
        return false;
    }

    @Override
    public boolean isUsersCreate() {
        return getRootAlbum().isUsersCreate();
    }

    @Override
    public boolean isUsersView() {
        return getRootAlbum().isUsersView();
    }

    public void setParentId(final int parentId) {
        this.parentId = parentId;
    }

    @Override
    public String toString() {
        return "SubAlbum [id=" + getId() + ", parentId=" + parentId + ", name="
                + getName() + ", ownerId=" + getOwnerId() + ", dateCreation="
                + getDateCreation() + ", dateUpdate=" + getDateUpdate() + "]";
    }

}
