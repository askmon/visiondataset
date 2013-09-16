package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import br.usp.ime.vision.dataset.util.AttachmentUtils;
import br.usp.ime.vision.dataset.util.Configs;

public class AttachmentUtilsTest {

    private static int ATTACHMENT_ID = 1;

    private final String dummycontent = "DUMMY";

    private File dummyFile;
    private String attachmentDir;

    private String attachment_file_name;

    @Before
    public void setUp() throws Exception {

        dummyFile = File.createTempFile("dummy", "dummy");
        attachmentDir = Configs.getAttachmentDir();
        attachment_file_name = attachmentDir + File.separatorChar + "1";
        final FileWriter writer = new FileWriter(dummyFile);

        writer.write(dummycontent);
        writer.close();

    }

    @After
    public void tearDown() throws Exception {

        dummyFile.delete();
        final File attachment = new File(attachment_file_name);
        attachment.delete();

    }

    @Test
    public void testGetFile() throws IOException {
        FileUtils.copyFile(dummyFile, new File(attachment_file_name));
        final File file = AttachmentUtils.getFile(ATTACHMENT_ID, "dummy");
        assertTrue(FileUtils.contentEquals(dummyFile, file));

    }

    @Test
    public void testSaveAndDeleteAttachment() {
        try {
            AttachmentUtils.saveAttachment(ATTACHMENT_ID, dummyFile);
            File attachment = new File(attachment_file_name);
            assertTrue(attachment.exists());
            assertEquals(dummyFile.length(), attachment.length());

            AttachmentUtils.deleteAttachment(ATTACHMENT_ID);
            attachment = new File(attachment_file_name);
            assertFalse(attachment.exists());
        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }

    @Test
    public void testSaveAttachment() {
        try {
            AttachmentUtils.saveAttachment(ATTACHMENT_ID, dummyFile);
            final File attachment = new File(attachment_file_name);
            assertTrue(attachment.exists());
            assertEquals(dummyFile.length(), attachment.length());

        } catch (final IOException e) {
            fail(e.getMessage());
        }
    }

}
