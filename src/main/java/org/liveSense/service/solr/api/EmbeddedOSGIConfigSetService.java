package org.liveSense.service.solr.api;

import org.apache.solr.common.SolrException;
import org.apache.solr.core.ConfigSetService;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.SolrResourceLoader;

import java.io.File;
import java.net.MalformedURLException;

public class EmbeddedOSGIConfigSetService extends ConfigSetService {


	private final File configSetBase;
	SolrResourceLoader loader;
	/**
	 * Create a new ConfigSetService.Default
	 * @param loader the CoreContainer's resource loader
	 * @param configSetBase the base directory under which to look for config set directories
	 */
	public EmbeddedOSGIConfigSetService(SolrResourceLoader loader, String configSetBase) {
		super(loader);
		this.loader = loader;
		this.configSetBase = resolveBaseDirectory(loader, configSetBase);
	}

	private File resolveBaseDirectory(SolrResourceLoader loader, String configSetBase) {
		File csBase = new File(configSetBase);
		if (!csBase.isAbsolute())
			csBase = new File(loader.getInstanceDir(), configSetBase);
		return csBase;
	}

	// for testing
	File getConfigSetBase() {
		return this.configSetBase;
	}

	@Override
	public SolrResourceLoader createCoreResourceLoader(CoreDescriptor cd) {
		String instanceDir = locateInstanceDir(cd);
		SolrResourceLoader confloader = loader;
		try {
			confloader = new EmbeddedOSGiClientDirectoryResourceLoader(
					instanceDir,
					new URLClassLoaderWrapper(loader.getClassLoader(), new File(instanceDir)));
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}

		return confloader;
	}

	@Override
	public String configName(CoreDescriptor cd) {
		return (cd.getConfigSet() == null ? "instancedir " : "configset ") + locateInstanceDir(cd);
	}

	protected String locateInstanceDir(CoreDescriptor cd) {
		String configSet = cd.getConfigSet();
		if (configSet == null)
			return cd.getInstanceDir();
		File configSetDirectory = new File(configSetBase, configSet);
		if (!configSetDirectory.exists() || !configSetDirectory.isDirectory())
			throw new SolrException(SolrException.ErrorCode.SERVER_ERROR,
					"Could not load configuration from directory " + configSetDirectory.getAbsolutePath());
		return configSetDirectory.getAbsolutePath();
	}

}
