package br.usp.ime.vision.dataset.dao.interfaces;

import java.util.List;

import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.AlbumTag;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;
import br.usp.ime.vision.dataset.entities.ImageTag;
import br.usp.ime.vision.dataset.entities.Tag;

/**
 * Interface for access to {@link Tag} related data.
 * 
 * @author Bruno Klava
 */
public interface TagDAO {

    /**
     * Adds the {@link Tag} <code>tagName</code> to the {@link Album} with the
     * given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param tagName
     *            the name of the new {@link Tag}
     * @return the <code>id</code> of the {@link Tag} created
     */
    public int addAlbumTag(int albumId, String tagName);

    /**
     * Adds a new {@link ImageAnnotation} to the {@link Image} with the given
     * <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param tagName
     *            the name of the new {@link ImageAnnotation}
     * @param x
     *            the <code>x</code> coordinate of the region associated to the
     *            {@link ImageAnnotation}
     * @param y
     *            the <code>y</code> coordinate of the region associated to the
     *            {@link ImageAnnotation}
     * @param width
     *            the <code>width</code> of the region associated to the
     *            {@link ImageAnnotation}
     * @param height
     *            the <code>height</code> of the region associated to the
     *            {@link ImageAnnotation}
     * @return the <code>id</code> of the {@link ImageAnnotation} created
     */
    public int addImageAnnotation(int imageId, String tagName, int x, int y,
            int width, int height);

    /**
     * Adds the {@link Tag} <code>tagName</code> to the {@link Image} with the
     * given <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param tagName
     *            the name of the new {@link Tag}
     * @return the <code>id</code> of the {@link Tag} created
     */
    public int addImageTag(int imageId, String tagName);

    /**
     * Deletes an {@link AlbumTag} from an {@link Album}.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param tagId
     *            the <code>id</code> of an {@link AlbumTag}
     * @return <code>true</code> if the {@link AlbumTag} deletion was successful
     */
    public boolean deleteAlbumTag(int albumId, int tagId);

    /**
     * Deletes an {@link ImageAnnotation} from an {@link Image}.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param tagId
     *            the <code>id</code> of an {@link ImageAnnotation}
     * @return <code>true</code> if the {@link ImageAnnotation} deletion was
     *         successful
     */
    public boolean deleteImageAnnotation(int imageId, int tagId);

    /**
     * Deletes an {@link ImageTag} from an {@link Image}.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param tagId
     *            the <code>id</code> of an {@link ImageTag}
     * @return <code>true</code> if the {@link ImageTag} deletion was successful
     */
    public boolean deleteImageTag(int imageId, int tagId);

    /**
     * Returns the {@link AlbumTag} with the given <code>tagId</code> of the
     * {@link Album} of the given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @param tagId
     *            the <code>id</code> of an {@link AlbumTag}
     * @return the {@link AlbumTag} with the given <code>tagId</code> of the
     *         {@link Album} of the given <code>albumId</code>
     */
    public AlbumTag getAlbumTag(int albumId, int tagId);

    /**
     * Returns a <code>List</code> of the {@link Tag}s of the {@link Album} of
     * the given <code>albumId</code>.
     * 
     * @param albumId
     *            the <code>id</code> of an {@link Album}
     * @return a <code>List</code> of the {@link Tag}s of the {@link Album} of
     *         the given <code>albumId</code>
     */
    public List<Tag> getAlbumTags(int albumId);

    /**
     * Returns the {@link ImageAnnotation} with the given <code>tagId</code> of
     * the {@link Image} of the given <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param tagId
     *            the <code>id</code> of an {@link ImageAnnotation}
     * @return the {@link ImageAnnotation} with the given <code>tagId</code> of
     *         the {@link Image} of the given <code>imageId</code>
     */
    public ImageAnnotation getImageAnnotation(int imageId, int tagId);

    /**
     * Returns a <code>List</code> of the {@link ImageAnnotation}s of the
     * {@link Image} of the given <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @return a <code>List</code> of the {@link ImageAnnotation}s of the
     *         {@link Image} of the given <code>imageId</code>
     */
    public List<ImageAnnotation> getImageAnnotations(int imageId);

    /**
     * Returns the {@link ImageTag} with the given <code>tagId</code> of the
     * {@link Image} of the given <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @param tagId
     *            the <code>id</code> of an {@link ImageTag}
     * @return the {@link ImageTag} with the given <code>tagId</code> of the
     *         {@link Image} of the given <code>imageId</code>
     */
    public ImageTag getImageTag(int imageId, int tagId);

    /**
     * Returns a <code>List</code> of the {@link Tag}s of the {@link Image} of
     * the given <code>imageId</code>.
     * 
     * @param imageId
     *            the <code>id</code> of an {@link Image}
     * @return a <code>List</code> of the {@link Tag}s of the {@link Image} of
     *         the given <code>imageId</code>
     */
    public List<Tag> getImageTags(int imageId);

    /**
     * Returns a <code>List</code> with the names of all {@link Tag}s utilized
     * in the system.
     * 
     * @return a <code>List</code> with all {@link Tag}s names
     */
    public List<String> getTagsNames();

    /**
     * Updates the {@link ImageAnnotation} with the given <code>tagId</code>.
     * 
     * @param tagId
     *            the <code>id</code> of an {@link ImageAnnotation}
     * @param tagName
     *            the new name for {@link ImageAnnotation}
     * @param x
     *            the new <code>x</code> coordinate of the region associated to
     *            the {@link ImageAnnotation}
     * @param y
     *            the new <code>y</code> coordinate of the region associated to
     *            the {@link ImageAnnotation}
     * @param width
     *            the new <code>width</code> of the region associated to the
     *            {@link ImageAnnotation}
     * @param height
     *            the new <code>height</code> of the region associated to the
     *            {@link ImageAnnotation}
     * @return <code>true</code> if the {@link ImageAnnotation} update was
     *         successful
     */
    public boolean updateAnnotation(int tagId, String tagName, int x, int y,
            int width, int height);

}
