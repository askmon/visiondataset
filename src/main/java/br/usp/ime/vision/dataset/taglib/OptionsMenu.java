package br.usp.ime.vision.dataset.taglib;

import java.io.IOException;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates an expandable/colapsable menu.
 * 
 * @author Bruno Klava
 */
public class OptionsMenu extends TagSupport {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(OptionsMenu.class);

    private String name;

    private String icon;

    @Override
    public int doEndTag() throws JspException {

        try {
            pageContext.getOut().write("<span class=\"menuHandler\">");
            pageContext.getOut()
                    .write("<img alt=\"" + name + "\" src=\"" + icon + "\" /> "
                            + name);
            pageContext.getOut().write("</span>");
            pageContext.getOut().write("</div>");

        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }

        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {

        try {

            pageContext.getOut().write("<div class=\"action actionMenu\">");

        } catch (final IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }

        return EVAL_BODY_INCLUDE;

    }

    public String getIcon() {
        return icon;
    }

    public String getName() {
        return name;
    }

    public void setIcon(final String icon) {
        this.icon = icon;
    }

    public void setName(final String name) {
        this.name = name;
    }
}
