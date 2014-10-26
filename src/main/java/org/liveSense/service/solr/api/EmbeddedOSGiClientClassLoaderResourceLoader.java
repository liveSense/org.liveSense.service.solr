package org.liveSense.service.solr.api;

import org.apache.solr.core.SolrResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class EmbeddedOSGiClientClassLoaderResourceLoader extends SolrResourceLoader {

	public static Logger log = LoggerFactory.getLogger(EmbeddedOSGiClientClassLoaderResourceLoader.class);

	private String instanceDir;

	public EmbeddedOSGiClientClassLoaderResourceLoader(String instanceDir, final ClassLoader parent) throws MalformedURLException {
		super(instanceDir,parent);
		classLoader = new URLClassLoaderWrapper(parent, new File(getConfigDir()));
		this.instanceDir = instanceDir;
	}


	@Override
	public InputStream openResource(String resource) {
		InputStream in = classLoader.getResourceAsStream(resource);
		// Try in config dir
		if ( in == null ) {
			in = classLoader.getResourceAsStream(getConfigDir()+resource);
		}
		if ( in == null ) {
			try {
				in = super.openResource(resource);
			} catch (IOException e) {
				log.error("openResource",e);
			}
		}
		return in;
	}

	public InputStream openConfig(String name) throws IOException {
		return openResource(name);
	}


	public InputStream openSchema(String name) throws IOException {
		return openResource(name);
	}


}