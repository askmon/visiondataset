package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertArrayEquals;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageAttachment;
import br.usp.ime.vision.dataset.util.Configs;
import br.usp.ime.vision.dataset.ws.ImageService;

public class ImageServiceTest {

    private ImageService imageService;
    private String image_3_file_name;
    String randomContent;

    @Before
    public void setUp() throws Exception {
        TestDatabaseSetup.setUp();
        imageService = new ImageService();
        image_3_file_name = Configs.getImagesDir() + File.separatorChar + "3";
        final File dir = new File(Configs.getImagesDir());
        dir.mkdirs();
        final FileWriter writer = new FileWriter(image_3_file_name);
        randomContent = RandomStringUtils.randomAscii(100);
        writer.write(randomContent);
        writer.flush();
        writer.close();
    }

    @After
    public void tearDown() throws Exception {
        TestDatabaseSetup.tearDown();
        imageService = null;
    }

    @Test
    public void testCreate() throws IOException {
        final List<ImageAttachment> before = DAOFactory.getAttachmentDAO().getImageAttachmentList(3);
        final String createAttachment = imageService.createAttachment(3, "\"create_test123.asd\"");
        assertEquals('"' + Configs.getServerURL() + "ws/attachment/33\"", createAttachment);

        final List<ImageAttachment> after = DAOFactory.getAttachmentDAO().getImageAttachmentList(3);
        assertEquals(before.size() + 1, after.size());

    }

    @Test(expected = WebApplicationException.class)
    public void testDontCreate() throws IOException {
        imageService.createAttachment(100, "dont.asd");
    }

    @Test
    public void testGetImage() throws IOException {
        final Response image = imageService.getImage(3);
        assertEquals(MediaType.APPLICATION_OCTET_STREAM_TYPE, image.getMetadata().get("Content-Type").get(0));
        final File imageFile = (File) image.getEntity();
        final FileReader reader = new FileReader(imageFile);

        final char[] buf = new char[100];
        reader.read(buf);
        assertEquals(randomContent, new String(buf));

    }

    @Test
    public void testList() {
        
        
        
        
        final String expected_s = "[\n" + "  \"http://localhost:8080/VisionDataset/ws/attachment/24\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/25\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/27\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/26\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/23\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/22\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/15\",\n"
                + "  \"http://localhost:8080/VisionDataset/ws/attachment/19\"\n" + "]";
        
        
        String[] expecteds = expected_s.split("\\s*,\\s*");
		Arrays.sort(expecteds);
		String[] actuals = imageService.list(3).split("\\s*,\\s*");
		for (int i = 0; i < expecteds.length; i++) {
			ArrayUtils.contains(actuals, expecteds[i]);
		}
        
    }

}
