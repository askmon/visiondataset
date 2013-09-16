package br.usp.ime.vision.dataset.servlet;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import br.usp.ime.vision.dataset.dao.DAOFactory;
import br.usp.ime.vision.dataset.entities.ImageScript;

/**
 * Servlet implementation class ScriptServlet
 */
public class ScriptServlet extends DownloadServlet {

    private static final long serialVersionUID = 6722919810565960747L;

    private static final String SCRIPT_ID_PARAMETER = "id";

    private static final Logger logger = LoggerFactory
            .getLogger(ScriptServlet.class);

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
     *      response)
     */
    @Override
    protected void doGet(final HttpServletRequest request,
            final HttpServletResponse response) throws ServletException,
            IOException {
        final String id_string = request.getParameter(SCRIPT_ID_PARAMETER);
        if ((id_string != null) && id_string.matches("\\d+")) {
            final Integer id = Integer.parseInt(id_string);
            final ImageScript script = DAOFactory.getScriptDAO()
                    .getScript(id);
            if (script != null) {
                if (userHasPermission(request, script)) {
                    final File file = script.getFile();
                    doFileResponse(response, file);
                }
                logger.info(
                        "Request for restricted content (script) from IP {}",
                        request.getRemoteAddr());
                response.sendError(HttpServletResponse.SC_FORBIDDEN);
                return;
            }

        }
        redirectToNotFound(response);
    }

    @Override
    protected void redirectToNotFound(final HttpServletResponse response)
            throws IOException {
        response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not Found.");
    }

    private boolean userHasPermission(final HttpServletRequest request,
            final ImageScript script) {
        return super.userHasPermission(request, script.getImageId());

    }

}
