package br.usp.ime.vision.dataset.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.entities.Image;

/**
 * Shows a mini thumbnail of the {@link Image}.
 * 
 * @author Bruno Klava
 */
public class MiniThumbnail extends TagSupport {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(MiniThumbnail.class);

    private Image image;

    @Override
    public int doStartTag() throws JspException {
        try {

            pageContext.getOut().write(
                    "<img class=\"miniThumbnail\" id=\"" + image.getId()
                            + "\" src=\"" + image.getThumbnailURL()
                            + "\" width=\"64\" height=\"64\"  alt=\""
                            + image.getId() + "\"/>");

        } catch (final java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }

        return SKIP_BODY;

    }

    public Image getImage() {
        return image;
    }

    public void setImage(final Image image) {
        this.image = image;
    }
}
