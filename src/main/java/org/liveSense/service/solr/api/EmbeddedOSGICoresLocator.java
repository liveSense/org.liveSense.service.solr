package org.liveSense.service.solr.api;

import org.apache.solr.core.CoreContainer;
import org.apache.solr.core.CoreDescriptor;
import org.apache.solr.core.CoresLocator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

public class EmbeddedOSGICoresLocator implements CoresLocator {
	Logger log = LoggerFactory.getLogger(EmbeddedOSGICoresLocator.class);

	@Override
	public void create(CoreContainer cc, CoreDescriptor... coreDescriptors) {
		log.info("Create core ");
	}

	@Override
	public void persist(CoreContainer cc, CoreDescriptor... coreDescriptors) {
		log.info("Persist core ");
	}

	@Override
	public void delete(CoreContainer cc, CoreDescriptor... coreDescriptors) {
		log.info("Delete core ");
	}

	@Override
	public void rename(CoreContainer cc, CoreDescriptor oldCD, CoreDescriptor newCD) {
		log.info("Rename core ");
	}

	@Override
	public void swap(CoreContainer cc, CoreDescriptor cd1, CoreDescriptor cd2) {
		log.info("Swap core ");
	}

	@Override
	public List<CoreDescriptor> discover(CoreContainer cc) {
		log.info("Discover core ");
		return new ArrayList<CoreDescriptor>();
	}
}
