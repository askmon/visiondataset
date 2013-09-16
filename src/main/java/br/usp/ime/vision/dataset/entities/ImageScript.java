/**
 * 
 */
package br.usp.ime.vision.dataset.entities;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import br.usp.ime.vision.dataset.util.ScriptUtils;
import br.usp.ime.vision.dataset.util.Configs;

/**
 * @author RafaelLopes
 * 
 */
public class ImageScript extends Script {

    /**
     * 
     */
    private static final long serialVersionUID = -8091029012461277982L;

    private int imageId;

    /**
     * @return the imageId
     */
    public int getImageId() {
        return imageId;
    }

    public URI getUri() {
        final String uri_string = String.format("%sws/script/%d",
                Configs.getServerURL(), getId());
        URI uri;
        try {
            uri = new URI(uri_string);
        } catch (final URISyntaxException e) {
            return null;
        }
        return uri;

    }

    public void saveScript(final File file) throws IOException {
        ScriptUtils.saveScript(getId(), file);
    }

    /**
     * @param imageId
     *            the imageId to set
     */
    public void setImageId(final int imageId) {
        this.imageId = imageId;
    }

    /*
     * (non-Javadoc)
     * 
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "ImageScript [imageId=" + imageId + ", " + super.toString()
                + "]";
    }

}
