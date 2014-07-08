package appnotifier.web.servlet;

import java.io.IOException;
import java.io.InputStream;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.IOUtils;

public class AppNotifierServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;

    private static final String TEMPLATE_PATH = "appnotifierTemplate.js";

    private static final String URL_ROOT_RGX = "\\[\\[URL_ROOT\\]\\]";

    private String jsToReturn;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        prepareJsToReturn(req);
        resp.setContentType("application/javascript");
        resp.getWriter().write(jsToReturn);
    }

    private void prepareJsToReturn(HttpServletRequest req) throws IOException {
        if (jsToReturn == null) {
            String urlRoot = getUrlRoot(req);
            InputStream templateResource = this.getClass().getClassLoader().getResourceAsStream(TEMPLATE_PATH);
            String template = IOUtils.toString(templateResource);
            templateResource.close();
            jsToReturn = template.replaceAll(URL_ROOT_RGX, urlRoot);
        }
    }

    private String getUrlRoot(HttpServletRequest req) {
        StringBuilder urlRootBuilder = new StringBuilder(req.getScheme());
        urlRootBuilder.append("://");
        urlRootBuilder.append(req.getServerName());
        urlRootBuilder.append(":").append(req.getServerPort());
        urlRootBuilder.append(req.getContextPath());
        String urlRoot = urlRootBuilder.toString();

        if (!urlRoot.endsWith("/")) {
            urlRoot += "/";
        }

        return urlRoot;
    }
}
