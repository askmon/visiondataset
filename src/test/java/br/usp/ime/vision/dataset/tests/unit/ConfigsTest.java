package br.usp.ime.vision.dataset.tests.unit;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.apache.commons.configuration.Configuration;
import org.junit.Test;

import br.usp.ime.vision.dataset.util.Configs;

public class ConfigsTest {

    @Test
    public void testGetAttachmentDir() {
        assertEquals("/tmp/var/local/visionDataset/attachments", Configs.getAttachmentDir());
    }

    @Test
    public void testGetConfDir() {
        assertEquals("/tmp/etc/visionDataset/conf", Configs.getConfDir());
    }

    @Test
    public void testGetConfig() {
        final Configuration config = Configs.getConfig();
        assertNotNull(config);
    }

    @Test
    public void testGetImagesDir() {
        assertEquals("/tmp/var/local/visionDataset/images", Configs.getImagesDir());
    }

    @Test
    public void testGetMessagesFile() {
        assertEquals("/tmp/etc/visionDataset/conf/bbb", Configs.getMessagesFile());
    }

    @Test
    public void testGetMimeExtensionsFile() {
        assertEquals("/tmp/etc/visionDataset/conf/aaa", Configs.getMimeExtensionsFile());
    }

    @Test
    public void testGetScriptsDir() {
        assertEquals("/tmp/var/local/visionDataset/scripts", Configs.getScriptsDir());
    }

    @Test
    public void testGetServerURL() {
        assertEquals("http://localhost:8080/VisionDataset/", Configs.getServerURL());
    }

    @Test
    public void testGetThumbnailsDir() {
        assertEquals("/tmp/var/local/visionDataset/thumbnails", Configs.getThumbnailsDir());
    }
    

    @Test
    public void testConnectionFactoryClass() {
        assertEquals("br.usp.ime.vision.dataset.dao.impl.ConnectionFactoryMockup", Configs.getConnectionFactoryClassName());
    }

}
