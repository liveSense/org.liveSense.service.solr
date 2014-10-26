package org.liveSense.service.solr.api;

import org.apache.solr.common.SolrException;
import org.apache.solr.core.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EmbeddedOSGICoreContainer extends CoreContainer {

	Logger log = LoggerFactory.getLogger(EmbeddedOSGICoreContainer.class);
	SolrResourceLoader loader;
	ConfigSolr config;
	CoresLocator locator;

	public EmbeddedOSGICoreContainer(SolrResourceLoader loader, ConfigSolr config, CoresLocator locator) {
		super(loader, config, locator);
		this.loader = loader;
		this.config = config;
		this.locator = locator;
	}

	/**
	 * Creates a new core based on a CoreDescriptor.
	 *
	 * @param dcore        a core descriptor
	 * @param publishState publish core state to the cluster if true
	 *
	 * @return the newly created core
	 */
	@Override
	public SolrCore create(CoreDescriptor dcore, boolean publishState) {

		try {

			if (zkSys.getZkController() != null) {
				zkSys.getZkController().preRegister(dcore);
			}

			EmbeddedOSGIConfigSetService service = new EmbeddedOSGIConfigSetService(loader, dcore.getInstanceDir()+ dcore.getName());
			ConfigSet coreConfig =  service.getConfig(dcore);
			log.info("Creating SolrCore '{}' using configuration from {}", dcore.getName(), coreConfig.getName());
			SolrCore core = new SolrCore(dcore, coreConfig);

			// always kick off recovery if we are in non-Cloud mode
			if (!isZooKeeperAware() && core.getUpdateHandler().getUpdateLog() != null) {
				core.getUpdateHandler().getUpdateLog().recoverFromLog();
			}

			registerCore(dcore.getName(), core, publishState);

			return core;

		}
		catch (Exception e) {
			coreInitFailures.put(dcore.getName(), new CoreLoadFailure(dcore, e));
			log.error("Error creating core ["+dcore.getName()+"]: {"+e.getMessage()+"}", e);
			throw new SolrException(SolrException.ErrorCode.SERVER_ERROR, "Unable to create core [" + dcore.getName() + "]", e);
		}

	}

}
