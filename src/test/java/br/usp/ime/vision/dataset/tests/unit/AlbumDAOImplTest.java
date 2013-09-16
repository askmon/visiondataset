package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.dao.interfaces.AlbumDAO;
import br.usp.ime.vision.dataset.entities.Album;
import br.usp.ime.vision.dataset.entities.Image;
import br.usp.ime.vision.dataset.entities.User;

public class AlbumDAOImplTest {

 

    private AlbumDAO dao;

    @Before
    public void setUp() throws Exception {
        TestDatabaseSetup.setUp();
        this.dao = DAOFactory.getAlbumDao();
    }

    @After
    public void tearDown() throws Exception {
        TestDatabaseSetup.tearDown();
        this.dao = null;
    }

    @Test
    public void testCreateAlbum() {
        Album album =  new Album();
        album.setCreatePermission(0);
        album.setName("NOME");
        album.setOwnerId(1);
        int createAlbum = dao.createAlbum(album );
        assertEquals(createAlbum, 2);
    }

    @Test
    public void testGetAlbumsUser() {
        List<Album> albumsUser = dao.getAlbumsUser(1);
        assertEquals(1, albumsUser.size());
        assertEquals("Teste", albumsUser.get(0).getName());
    }

    @Test
    public void testGetAlbumsUserCreate() {
        List<Album> list = dao.getAlbumsUserCreate(1);
        assertEquals(1, list.size());
        assertEquals("Teste", list.get(0).getName());
    }

    @Test
    public void testGetPublicAlbums() {
        List<Album> list = dao.getPublicAlbums();
        assertEquals(0, list.size());
    }

    @Test
    public void testGetAllAlbums() {
        List<Album> list = dao.getAllAlbums();
        assertEquals(1, list.size());
        assertEquals("Teste", list.get(0).getName());
    }

    @Test
    public void testGetAlbum() {
        Album album = dao.getAlbum(1);
        assertEquals("Teste", album.getName());
        assertEquals(1, album.getId());
    }

    @Test
    public void testGetAlbumForImage() {
        Album album = dao.getAlbumForImage(3);
        assertEquals("Teste", album.getName());
        assertEquals(1, album.getId());
        
    }

    @Test
    public void testAddImageToAlbum() {
        int image = dao.addImageToAlbum(1, 1, "png");
        assertEquals(7, image);
        Image getimage = dao.getImage(7);
        assertEquals(1,getimage.getAlbum().getId());
        assertEquals(7,getimage.getId());
        assertEquals(1,getimage.getOwnerId());
    }

    @Test
    public void testGetImages() {
        List<Image> images = dao.getImages(1);
        assertEquals(4,images.size());
        assertEquals(3, images.get(0).getId());
    }


    @Test
    public void testDeleteImage() {
        dao.deleteImage(5,1);
        List<Image> images = dao.getImages(1);
        assertEquals(3,images.size());
        assertEquals(3, images.get(0).getId());
    }


    @Test
    public void testRenameAlbum() {
        String novonome = "sdjfsdklfjh";
        dao.renameAlbum(1, novonome);
        Album album = dao.getAlbum(1);
        assertEquals(novonome, album.getName());
    }

    @Test
    public void testGetUsersPermission() {
        List<User> users = dao.getUsersWithPermission(1);
        assertEquals(0, users.size());
        List<User> usersWithoutPermission = dao.getUsersWithoutPermission(1);
        assertEquals(0, usersWithoutPermission.size());
    }

}
