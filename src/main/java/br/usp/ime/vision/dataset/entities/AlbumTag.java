package br.usp.ime.vision.dataset.entities;

import br.usp.ime.vision.dataset.dao.DAOFactory;

/**
 * Represents an {@link Tag} associated to an {@link Album}.
 * 
 * @author Bruno Klava
 */
public class AlbumTag extends Tag {

    private int albumId;

    /**
     * Deletes this {@link AlbumTag} from its parent {@link Album}.
     * 
     * @return <code>true</code> if this {@link AlbumTag} deletion was
     *         successful
     */
    @Override
    public boolean deleteTag() {
        return DAOFactory.getTagDao().deleteAlbumTag(getAlbumId(), getId());
    }

    public int getAlbumId() {
        return albumId;
    }

    public void setAlbumId(final int albumId) {
        this.albumId = albumId;
    }

    @Override
    public String toString() {
        return "AlbumTag [" + getTagName() + ", id=" + getId() + ", albumId="
                + albumId + "]";
    }

}
