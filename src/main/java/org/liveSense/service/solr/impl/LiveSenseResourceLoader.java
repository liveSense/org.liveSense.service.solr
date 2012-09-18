package org.liveSense.service.solr.impl;

import java.io.IOException;
import java.io.InputStream;

import org.apache.solr.core.SolrResourceLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LiveSenseResourceLoader extends SolrResourceLoader {

	public static Logger log = LoggerFactory.getLogger(LiveSenseResourceLoader.class);


	public LiveSenseResourceLoader( String instanceDir, ClassLoader parent ) {
		super(instanceDir,parent);
	}


	@Override
	public InputStream openResource(String resource) {
		InputStream in = this.getClass().getClassLoader().getResourceAsStream(resource);
		if ( in == null ) {
			try {
				in = super.openResource(resource);
			} catch (IOException e) {
				log.error("openResource",e);
			}
		}
		return in;
	}
	/*
	ClassLoader classLoader = null;
	String instanceDir = "";

	public LiveSenseResourceLoader(String instanceDir, ClassLoader classLoader) {
		super(instanceDir, classLoader);
		this.classLoader = classLoader;
		this.instanceDir = instanceDir;
	}


	public LiveSenseResourceLoader(String instanceDir) {
		super(instanceDir, LiveSenseResourceLoader.class.getClassLoader());
		//reloadLuceneSPI();

		this.classLoader = LiveSenseResourceLoader.class.getClassLoader();
		this.instanceDir = instanceDir;
	}

	@Override
	public <T> T newInstance(String name, Class<T> clazz) {
		if (clazz == null) {
			log.error("Class type is not defined: "+name);
			return null;
		}
		try {
			return clazz.newInstance();
		} catch (InstantiationException e) {
			log.error("Could not instantiate class: "+name+" "+clazz.getName(), e);
		} catch (IllegalAccessException e) {
			log.error("Could not instantiate class: "+name+" "+clazz.getName(), e);
		}
		return null;
	}

	@Override
	public InputStream openResource(String resource) throws IOException {
		try {
			File f0 = new File(resource);
			File f = f0;
			if (!f.isAbsolute()) {
				// try $CWD/$configDir/$resource
				f = new File(getConfigDir() + resource);
			}
			if (f.isFile() && f.canRead()) {
				return new FileInputStream(f);
			} else if (f != f0) { // no success with $CWD/$configDir/$resource
				if (f0.isFile() && f0.canRead())
					return new FileInputStream(f0);
			}
		} catch (Throwable th) {
		}

		InputStream in = classLoader.getResourceAsStream(resource);
		if (in == null)
			in = classLoader.getResourceAsStream(getConfigDir() + resource);

		if ( in == null ) {
			log.error("Could not find resource: "+resource);
			throw new IOException("Could not find resource: "+resource);
			//			try {
			//				in = super.openResource(resource);
			//			} catch (IOException e) {
			//				log.error("openResource",e);
			//			}
		}
		return in;
	}

	public String getConfigDir() {
		return instanceDir + "conf/";
	}
	 */
}