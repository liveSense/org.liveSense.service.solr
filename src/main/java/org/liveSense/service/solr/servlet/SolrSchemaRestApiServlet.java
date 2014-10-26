package org.liveSense.service.solr.servlet;

import org.apache.felix.scr.annotations.Component;
import org.apache.felix.scr.annotations.Properties;
import org.apache.felix.scr.annotations.Property;
import org.apache.felix.scr.annotations.Service;
import org.restlet.ext.servlet.ServerServlet;

import javax.servlet.Servlet;


@Component(immediate = true, metatype = true)
@Service(value = Servlet.class)
@Properties(value = {
		@Property(name = "alias", value = "/solr/schema"),
		@Property(name = "servlet-name", value = "solr-rest-schema-api-servlet"),
		@Property(name = "org.restlet.application", value = "org.apache.solr.rest.SolrSchemaRestApi")
})

public class SolrSchemaRestApiServlet extends ServerServlet {

}
