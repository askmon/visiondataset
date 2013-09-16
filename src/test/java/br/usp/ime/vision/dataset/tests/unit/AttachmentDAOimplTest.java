package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.sql.SQLException;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.dao.impl.AttachmentDAOImpl;
import br.usp.ime.vision.dataset.entities.ImageAttachment;

public class AttachmentDAOimplTest {

    AttachmentDAOImpl dao;

    @Before
    public void setUp() throws Exception {
        TestDatabaseSetup.setUp();
        dao = (AttachmentDAOImpl) DAOFactory.getAttachmentDAO();

    }

    @After
    public void tearDown() throws SQLException {
        TestDatabaseSetup.tearDown();
    }

    @Test
    public void testGetAttachment() {
        ImageAttachment attachment = dao.getAttachment(19);
        assertEquals(19, attachment.getId());
        assertEquals("teste.png", attachment.getName());
        assertEquals(3, attachment.getImageId());
    }

    @Test
    public void testGetNullAttachment() {
        ImageAttachment attachment = dao.getAttachment(33);
        assertEquals(null, attachment);
    }

    @Test
    public void testAddAttachmentAndList() {
        String name = "__TEST__.txt";
        int id = dao.addImageAttachment(3, name);
        assertEquals(33, id);
        List<ImageAttachment> imageAttachmentList = dao.getImageAttachmentList(3);
        assertEquals(9, imageAttachmentList.size());
        ImageAttachment imageAttachment = imageAttachmentList.get(imageAttachmentList.size() - 1);
        assertEquals(33, imageAttachment.getId());
        assertEquals(name, imageAttachment.getName());
    }

    @Test
    public void testDelete() {
        boolean yep = dao.deleteAttachment(19);
        assertTrue(yep);

        ImageAttachment attachment = dao.getAttachment(19);
        assertEquals(null, attachment);

    }
    
    @Test
    public void testDeleteAndList(){
        List<ImageAttachment> imageAttachmentList = dao.getImageAttachmentList(3);
        assertEquals(8, imageAttachmentList.size());
        
        boolean yep = dao.deleteAttachment(19);
        assertTrue(yep);
        
        imageAttachmentList = dao.getImageAttachmentList(3);
        assertEquals(7, imageAttachmentList.size());
        
        
    }


}
