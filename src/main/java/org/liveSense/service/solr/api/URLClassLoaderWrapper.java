package org.liveSense.service.solr.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Enumeration;

public class URLClassLoaderWrapper extends URLClassLoader {

	Logger log = LoggerFactory.getLogger(URLClassLoaderWrapper.class);
	ClassLoader inh = null;

	private String root;

	public URLClassLoaderWrapper(ClassLoader parent, File root) throws MalformedURLException {
		super(new URL[]{root.toURI().toURL()});
		inh = parent;
		this.root = root.getAbsolutePath();
	}
	
	@Override
	public URL findResource(String name) {
		URL res = super.findResource(name);
		if (res == null) {
			return inh.getResource(name);
		} else {
			return res;
		}
	}
	
	@Override
	public URL getResource(String name) {
		URL res = super.getResource(name);
		if (res == null) {
			return inh.getResource(name);
		} else {
			return res;
		}
	}
	
	@Override
	public Enumeration<URL> findResources(String name) throws IOException {
		Enumeration<URL> res = super.findResources(name);
		if (res == null) {
			return inh.getResources(name);
		} else {
			return res;
		}
	}

	@Override
	public URL[] getURLs() {
		return super.getURLs();
	}

	@Override
	public Enumeration<URL> getResources(String name) throws IOException {
		Enumeration<URL> res = super.getResources(name);
		if (res == null) {
			return inh.getResources(name);
		} else {
			return res;
		}
	}

	/** Opens any resource by its name.
	 * By default, this will look in multiple locations to load the resource:
	 * $configDir/$resource (if resource is not absolute)
	 * $CWD/$resource
	 * otherwise, it will look for it in any jar accessible through the class loader.
	 * Override this method to customize loading resources.
	 *@return the stream for the named resource
	 */
	@Override
	public InputStream getResourceAsStream(String resource) {
		InputStream is=null;
		try {
			File f0 = new File(resource), f = f0;
			if (!f.isAbsolute()) {
				// try $CWD/$configDir/$resource
				f = new File(getConfigDir() + resource).getAbsoluteFile();
			}
			boolean found = f.isFile() && f.canRead();
			if (!found) { // no success with $CWD/$configDir/$resource
				f = f0.getAbsoluteFile();
				found = f.isFile() && f.canRead();
			}
			// check that we don't escape instance dir
			if (found) {
				if (!Boolean.parseBoolean(System.getProperty("solr.allow.unsafe.resourceloading", "false"))) {
					final URI instanceURI = new File(root).getAbsoluteFile().toURI().normalize();
					final URI fileURI = f.toURI().normalize();
					if (instanceURI.relativize(fileURI) == fileURI) {
						// no URI relativize possible, so they don't share same base folder
						throw new IOException("For security reasons, SolrResourceLoader cannot load files from outside the instance's directory: " + f +
								"; if you want to override this safety feature and you are sure about the consequences, you can pass the system property "+
								"-Dsolr.allow.unsafe.resourceloading=true to your JVM");
					}
				}
				// relativize() returned a relative, new URI, so we are fine!
				return new FileInputStream(f);
			}
			// Delegate to the class loader (looking into $INSTANCE_DIR/lib jars).
			// We need a ClassLoader-compatible (forward-slashes) path here!
			is = inh.getResourceAsStream(resource.replace(File.separatorChar, '/'));
			// This is a hack just for tests (it is not done in ZKResourceLoader)!
			// -> the getConfigDir's path must not be absolute!
			if (is == null && System.getProperty("jetty.testMode") != null && !new File(getConfigDir()).isAbsolute()) {
				is = inh.getResourceAsStream((getConfigDir() + resource).replace(File.separatorChar, '/'));
			}
		} catch (IOException ioe) {
			//throw ioe;
		} catch (Exception e) {
			//throw new IOException("Error opening " + resource, e);
		}
		if (is==null) {
			//throw new IOException("Can't find resource '" + resource + "' in classpath or '" + new File(getConfigDir()).getAbsolutePath() + "'");
		}
		return is;
	}

	public String getConfigDir() {
		return root + "conf" + File.separator;
	}

	@Override
	public Class<?> loadClass(String name) throws ClassNotFoundException {
		try {
			return inh.loadClass(name);
		} catch (ClassNotFoundException e) {
			return super.loadClass(name);
		}
	}
}
