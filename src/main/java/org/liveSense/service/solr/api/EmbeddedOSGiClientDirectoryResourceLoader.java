package org.liveSense.service.solr.api;

import org.apache.solr.core.SolrResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;

public class EmbeddedOSGiClientDirectoryResourceLoader extends SolrResourceLoader {

	public static Logger log = LoggerFactory.getLogger(EmbeddedOSGiClientDirectoryResourceLoader.class);

	private String instanceDir;

	public EmbeddedOSGiClientDirectoryResourceLoader(String instanceDir, final URLClassLoaderWrapper parent) throws MalformedURLException {
		super(instanceDir, parent);
		this.classLoader = parent;
		this.instanceDir = instanceDir;
	}


	public InputStream openConfig(String name) throws IOException {
		return openResource(name);
	}


	public InputStream openSchema(String name) throws IOException {
		return openResource(name);
	}

	public String getConfigDir() {
		return instanceDir + "conf" + File.separator;
	}

	static final String empty[] = new String[0];

	@Override
	public <T> Class<? extends T> findClass(String cname, Class<T> expectedType) {
		return findClass(cname, expectedType, empty);
	}

	public <T> Class<? extends T> findClass(String cname, Class<T> expectedType, String... subpackages) {
		try {
			return (Class<? extends T>) this.classLoader.loadClass(cname);
		} catch (Throwable th) {
			return super.findClass(cname, expectedType, subpackages);
		}
	}

	@Override
	public <T> T newInstance(String name, Class<T> expectedType) {
		return newInstance(name, expectedType, empty);
	}

	@Override
	public <T> T newInstance(String cname, Class<T> expectedType, String ... subpackages) {
		return super.newInstance(cname, expectedType, subpackages);
	}

}