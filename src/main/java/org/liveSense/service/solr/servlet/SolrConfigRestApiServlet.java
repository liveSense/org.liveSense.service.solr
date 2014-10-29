package org.liveSense.service.solr.servlet;

import org.apache.felix.scr.annotations.*;
import org.osgi.framework.ServiceRegistration;
import org.osgi.service.component.ComponentContext;
import org.restlet.ext.servlet.ServerServlet;

import javax.servlet.Servlet;


@Component(immediate = true, metatype = true)
@Service(value = Servlet.class)
@Properties(value = {
		@Property(name = "alias", value = "/solr/config"),
		@Property(name = "servlet-name", value = "solr-rest-config-api-servlet"),
		@Property(name = "org.restlet.application", value = "org.apache.solr.rest.SolrSchemaRestApi")
})

public class SolrConfigRestApiServlet extends ServerServlet {

	private ServiceRegistration registration;

	@Activate
	protected void activate(ComponentContext context) {
		this.registration = context.getBundleContext().registerService(Servlet.class.getName(), this, context.getProperties());
	}

	@Deactivate
	protected void deactivate(ComponentContext context) {
		this.registration.unregister();
	}
}
