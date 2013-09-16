package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.Response;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.vision.dataset.entities.ImageAttachment;
import br.usp.ime.vision.dataset.util.Configs;
import br.usp.ime.vision.dataset.util.JsonUtils;
import br.usp.ime.vision.dataset.ws.AttachmentService;

import com.google.gson.Gson;

public class AttachmentServiceTest {

    private AttachmentService attachmentService;
    private File attachmentf;

    @Before
    public void setUp() throws Exception {
        TestDatabaseSetup.setUp();
        attachmentService = new AttachmentService();
        attachmentf = new File(Configs.getAttachmentDir() + File.separatorChar
                + "19");
        final File attachmentDir = new File(Configs.getAttachmentDir());
        attachmentDir.mkdirs();
        attachmentf.createNewFile();
        final FileWriter writer = new FileWriter(attachmentf);
        writer.append("attachment : " + RandomStringUtils.random(10));
        writer.close();

    }

    @After
    public void tearDown() throws Exception {
        TestDatabaseSetup.tearDown();
        attachmentf.delete();
    }

    @Test
    public void testGetAttachment() {
        String json = attachmentService.getAttachment(19);
        final Gson converter = JsonUtils.getJsonConverter();
        converter.fromJson(json, ImageAttachment.class);
        json = json.replace(' ', '.');
        json = json.replace('\n', '.');
        assertEquals(
                "{...\"imageId\":.3,...\"id\":.19,...\"dateCreation\":.\"Jul.12,.2011.7:23:46.PM\",...\"name\":.\"teste.png\".}",
                json);
        json = attachmentService.getAttachment(100);
        assertEquals("", json);

    }

    @Test
    public void testGetAttachmentFile() throws IOException {
        final Response response = attachmentService.getAttachmentFile(19);
        final MultivaluedMap<String, Object> metadata = response.getMetadata();
        assertEquals("attachment; filename=teste.png",
                metadata.get("Content-Disposition").get(0));
        assertTrue(FileUtils.contentEquals(attachmentf,
                (File) response.getEntity()));

    }

    @Test
    public void testUpdateAttachmentFile() throws IOException {
        final File tmpF = File.createTempFile("test", "tmp");
        final FileWriter writer = new FileWriter(tmpF);
        writer.append("novo 1234" + RandomStringUtils.random(10));
        writer.close();
        attachmentService.updateAttachmentFile(19, tmpF, null);
        assertTrue(FileUtils.contentEquals(attachmentf, tmpF));
    }

}
