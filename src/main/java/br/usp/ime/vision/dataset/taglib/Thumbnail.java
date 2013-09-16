package br.usp.ime.vision.dataset.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.entities.Image;

/**
 * Shows a thumbnail with a link to the image and available options.
 * 
 * @author Bruno Klava
 */
public class Thumbnail extends TagSupport {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(Thumbnail.class);

    private Image image;

    private String delete;

    private String deleteText;

    @Override
    public int doStartTag() throws JspException {
        try {

            final String hideAll = "$('.thumbnailOptions').hide();";

            final String show = hideAll + " $('#options" + image.getId()
                    + "').show();";

            final String hide = hideAll;

            pageContext.getOut().write(
                    "<span class=\"thumbnail\" onmouseover=\"" + show
                            + "\" onmouseout=\"" + hide + "\">");

            pageContext.getOut().write(
                    "<span id=\"options" + image.getId()
                            + "\" class=\"thumbnailOptions\">");

            if (getDelete() != null) {
                pageContext.getOut().write(
                        "<a href=\"#\" onclick=\"confirmDelete('"
                                + getDeleteText() + "', " + image.getId()
                                + ", '" + getDelete()
                                + "'); return false;\" title=\""
                                + getDeleteText()
                                + "\"><img src=\"img/icons/delete.png\" alt=\""
                                + getDeleteText() + "\"/></a>");
            }

            pageContext.getOut().write("</span>");

            pageContext.getOut().write(
                    "<a href=\"" + image.getImageDetailURL() + "\">");
            pageContext.getOut().write(
                    "<img src=\"" + image.getThumbnailURL()
                            + "\" width=\"128\" height=\"128\" alt=\""
                            + image.getId() + "\"/>");
            pageContext.getOut().write("</a>");

            pageContext.getOut().write("</span>");

        } catch (final java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }

        return SKIP_BODY;

    }

    public String getDelete() {
        return delete;
    }

    public String getDeleteText() {
        return deleteText;
    }

    public Image getImage() {
        return image;
    }

    public void setDelete(final String delete) {
        this.delete = delete;
    }

    public void setDeleteText(final String deleteText) {
        this.deleteText = deleteText;
    }

    public void setImage(final Image image) {
        this.image = image;
    }
}
