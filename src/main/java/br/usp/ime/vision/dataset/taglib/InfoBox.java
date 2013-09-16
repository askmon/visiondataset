package br.usp.ime.vision.dataset.taglib;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspTagException;
import javax.servlet.jsp.tagext.TagSupport;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Creates an info box.
 * 
 * @author Bruno Klava
 */
public class InfoBox extends TagSupport {

    private static final long serialVersionUID = 1L;

    private static Logger logger = LoggerFactory.getLogger(InfoBox.class);

    @Override
    public int doEndTag() throws JspException {
        try {
            pageContext.getOut().write("</li></ul>");
        } catch (final java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage(), e);
        }
        return EVAL_PAGE;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            pageContext.getOut().write("<ul class=\"infoMessage\"><li>");
        } catch (final java.io.IOException e) {
            logger.error(e.getMessage(), e);
            throw new JspTagException("IO Error:" + e.getMessage());
        }
        return EVAL_BODY_INCLUDE;
    }

}
