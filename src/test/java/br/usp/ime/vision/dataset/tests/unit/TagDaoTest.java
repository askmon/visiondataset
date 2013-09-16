package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.dao.interfaces.TagDAO;
import br.usp.ime.vision.dataset.entities.AlbumTag;
import br.usp.ime.vision.dataset.entities.ImageAnnotation;
import br.usp.ime.vision.dataset.entities.ImageTag;
import br.usp.ime.vision.dataset.entities.Tag;

public class TagDaoTest {

    private TagDAO dao;

    @Before
    public void setUp() throws Exception {
        TestDatabaseSetup.setUp();
        this.dao = DAOFactory.getTagDao();
    }

    @After
    public void tearDown() throws Exception {
        TestDatabaseSetup.tearDown();
        this.dao = null;
    }

    @Test
    public void testAddAlbumTag() {
        int id = dao.addAlbumTag(1, "album tag");
        assertEquals(2, id);
        List<Tag> albumTags = dao.getAlbumTags(1);
        assertEquals(2, albumTags.size());
        assertEquals(id, albumTags.get(0).getId());
        assertEquals("album tag", albumTags.get(0).getTagName());
        
        AlbumTag albumTag = dao.getAlbumTag(1, id);
        assertEquals(albumTag,albumTags.get(0));
        
        dao.deleteAlbumTag(1, id);
        albumTags = dao.getAlbumTags(1);
        assertEquals(1, albumTags.size());
        assertEquals(1, albumTags.get(0).getId());
        assertEquals("test", albumTags.get(0).getTagName());
        
        assertNull(dao.getAlbumTag(1, id));
        
    }

    @Test
    public void testAddImageAnnotation() {
        int id = dao.addImageAnnotation(3, "image tag", 0, 10, 11, 1);
        List<ImageAnnotation> list = dao.getImageAnnotations(3);
        assertEquals(1, list.size());
        assertEquals(11, list.get(0).getWidth());
        
        ImageAnnotation ia = dao.getImageAnnotation(3, id);
        assertEquals(ia,list.get(0));
        
        dao.updateAnnotation(id, "novo", 6, 6, 6, 6);
        ia = dao.getImageAnnotation(3, id);
        assertEquals("novo", ia.getTagName());
        assertEquals(6, ia.getHeight());
        assertEquals(6, ia.getWidth());
        assertEquals(6, ia.getX());
        assertEquals(6, ia.getY());
        assertEquals(3, ia.getImageId());
        
        
        
        dao.deleteImageAnnotation(3, id);
        list = dao.getImageAnnotations(3);
        assertEquals(0, list.size());
        
        assertNull( dao.getImageAnnotation(3, id));
    }
    
    @Test
    public void testImageTag() {
        int id = dao.addImageTag(3, "___123___");
        List<Tag> list = dao.getImageTags(3);
        assertEquals(3, list.size());
        assertEquals("___123___", list.get(0).getTagName());
        
        ImageTag imageTag = dao.getImageTag(3, id);
        assertEquals(imageTag, list.get(0));
        
        dao.deleteImageTag(3, id);
        list = dao.getImageTags(3);
        assertEquals(2, list.size());
        
        imageTag = dao.getImageTag(3, id);
        assertNull(imageTag);
        
        
    }

    @Test
    public void testGetTagsNames() {
        List<String> list = dao.getTagsNames();
        assertEquals("test", list.get(0));
        assertEquals("asdf", list.get(1));
        assertEquals("bdgdgbfg", list.get(2));
        assertEquals(3, list.size());
        
    }

}
