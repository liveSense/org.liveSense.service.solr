package org.liveSense.service.solr.servlet;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.felix.scr.annotations.*;
import org.apache.sling.commons.mime.MimeTypeService;
import org.apache.sling.commons.osgi.PropertiesUtil;
import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.SolrCore;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Component(immediate = true, metatype = true)
@Service(value = Servlet.class)
@Properties(value = {
		@Property(name = "alias", value = "/solr"),
		@Property(name = "servlet-name", value = "solr-admin-content-servlet"),
		@Property(name = "defaultIndex", value = "/admin.html")

})
public class WhiteboardLoadAdminUIServlet extends HttpServlet {

	private static final long serialVersionUID = 3527994797153914038L;
	Logger log = LoggerFactory.getLogger(WhiteboardLoadAdminUIServlet.class);

	private ServiceRegistration registration;
	private String defaultIndex = "/admin.html";
	private String alias = "/solr";

	@Reference
	CoreContainer coreContainer;

	@Reference
	private MimeTypeService mimeTypeProvider;

	@Activate
	protected void activate(ComponentContext context) {
		defaultIndex = PropertiesUtil.toString(context.getProperties().get("defaultIndex"), "/admin.html");
		alias = PropertiesUtil.toString(context.getProperties().get("alias"), "/solr");
		this.registration = context.getBundleContext().registerService(Servlet.class.getName(), this, context.getProperties());
	}

	@Deactivate
	protected void deactivate() {
		this.registration.unregister();
	}

	@Override
	public void doGet(HttpServletRequest request,
					  HttpServletResponse response)
			throws IOException {

		String path = getUrlPath(request);
		if (StringUtils.isEmpty(path) || path.equalsIgnoreCase("/")) {
			redirectToIndex(request, response);
			return;
		}

		InputStream is = this.getClass().getClassLoader().getResourceAsStream(path);
		if (is == null) {
			response.sendError(HttpServletResponse.SC_NOT_FOUND, "Not found");
			return;
		}

		String extension = FilenameUtils.getExtension(path);
		String contentType = mimeTypeProvider.getMimeType(extension);

		if(is != null && coreContainer != null) {
			try {
				if (path.endsWith("admin.html")) {
					response.setCharacterEncoding("UTF-8");
					response.setContentType(contentType);
					Writer out = new OutputStreamWriter(response.getOutputStream(), StandardCharsets.UTF_8);
					String html = IOUtils.toString(is, "UTF-8");
					Package pack = SolrCore.class.getPackage();

					String[] search = new String[] {
							"${contextPath}",
							"${path}",
							"${adminPath}",
							"${version}"
					};
					String[] replace = new String[] {
							StringEscapeUtils.escapeJavaScript(alias),
							alias,
							StringEscapeUtils.escapeJavaScript(coreContainer.getAdminPath()),
							StringEscapeUtils.escapeJavaScript(pack.getSpecificationVersion())
					};

					out.write( StringUtils.replaceEach(html, search, replace) );
					out.flush();
				} else {
					response.setContentType(contentType);
					IOUtils.copy(is, response.getOutputStream());
				}

			} finally {
				IOUtils.closeQuietly(is);
			}
		} else {
			response.sendError(404);
		}
	}

	private String getUrlPath(HttpServletRequest req) {
		// WebSphere: Request URI: /mpsdv1/trendallocation/index.html Path: /trendallocation/index.html Context Path: /mpsdv1
		String path = "";
		if (StringUtils.isNotEmpty(req.getContextPath())) {
			path = req.getRequestURI().substring(req.getContextPath().length());
		} else {
			path = req.getRequestURI();
		}
		if (path.startsWith(alias)) {
			path = path.substring(alias.length());
		}
		return path;
	}

	private void redirectToIndex(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String url = response.encodeRedirectURL(request.getContextPath()) + defaultIndex;
		response.sendRedirect(url);
	}

}
