package br.usp.ime.vision.dataset.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.entities.User;
import br.usp.ime.vision.dataset.util.Configs;

/**
 * Servlet that loads image files (outside of the web context) checking if the
 * logged {@link User} has permission to view the requested image. If the
 * {@link User} doesn't have the necessary permission his/her IP is logged for
 * security reasons.
 * 
 * @author BrunoKlava
 */
public class ImagesServlet extends DownloadServlet {

    private static final long serialVersionUID = -3137812172833289845L;

    private static final Logger logger = LoggerFactory
            .getLogger(ImagesServlet.class);

    private String servletPath;

    private String requestURL;

    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {

        requestURL = request.getRequestURL().toString();
        servletPath = request.getServletPath();
        logger.debug("Looking for {}", requestURL);

        final boolean image = servletPath.substring(1).equals("images");

        String id = requestURL.substring(1 + requestURL
                .lastIndexOf(File.separatorChar));
        id = id.substring(0, id.lastIndexOf('.'));

        if (!userHasPermission(request, Integer.parseInt(id))) {
            redirectToNotFound(response);
            logger.info("Request for restricted content ({}) from IP {}", id,
                    request.getRemoteAddr());
            return;
        }

        try {

            final String dir = image ? Configs.getImagesDir() : Configs
                    .getThumbnailsDir();

            final String filename = dir + File.separator + id;
            doFileResponse(response, filename);

        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }

    }

    @Override
    protected void redirectToNotFound(final HttpServletResponse response)
            throws IOException {
        response.sendRedirect(requestURL.substring(0,
                requestURL.indexOf(servletPath))
                + "/img/extra/notfound.png");
    }

}
