package org.liveSense.service.solr.impl;

import java.io.InputStream;

import org.apache.solr.core.SolrResourceLoader;

public class LiveSenseResourceLoader extends SolrResourceLoader {

  static final String[] packages = {"","analysis.","schema.","handler.","search.","update.","core.","response.","request.","update.processor.","util.", "spelling.", "handler.component.", "handler.dataimport." };
  static final String project = "solr";
  static final String base = "org.apache" + "." + project;

  

  public LiveSenseResourceLoader( String instanceDir, ClassLoader parent ) {
    super(instanceDir,parent);
  }

  
  @Override
  public InputStream openResource(String resource) {
    InputStream in = this.getClass().getClassLoader().getResourceAsStream(resource);
    if ( in == null ) {
 //     try {
		in = super.openResource(resource);
//	} catch (IOException e) {
//		log.error("openResource",e);
//	}
    }
    return in;
  }

}
