# [liveSense :: Service :: Solr based search service. - org.liveSense.service.solr](http://github.com/liveSense/org.liveSense.service.solr)
## Description
A Solr embeded server service available or references a remote server. (Derived from Sakai Nakumara - https://github.com/ieb/solr)
## Exported packages
* org.liveSense.service.solr.api(1.0.0.SNAPSHOT)
* org.liveSense.service.solr.impl(1.0.0.SNAPSHOT)
## Dependencies
* __System Bundle - org.apache.felix.framework (3.0.8)__
	* javax.management
	* javax.management.openmbean
	* javax.management.remote
	* javax.naming
	* javax.swing
	* javax.xml.namespace
	* javax.xml.parsers
	* javax.xml.transform
	* javax.xml.transform.dom
	* javax.xml.xpath
	* org.osgi.framework
	* org.osgi.service.packageadmin
	* org.w3c.dom
	* org.xml.sax
* __ZooKeeper Bundle - org.apache.hadoop.zookeeper (3.4.4)__
	* org.apache.zookeeper
* __[liveSense :: Core - org.liveSense.core](http://github.com/liveSense/org.liveSense.core) (1.0.1.SNAPSHOT)__
	* org.liveSense.core
* __Logback Classic Module - ch.qos.logback.classic (1.0.3)__
	* org.slf4j.impl
* __[liveSense :: Framewrork :: Apache Solr Indexer/Search engine - org.liveSense.framework.solr](http://github.com/liveSense/org.liveSense.framework.solr) (4.0.0.1-SNAPSHOT)__
	* org.apache.lucene.analysis.util
	* org.apache.lucene.codecs
	* org.apache.lucene.codecs.lucene40
	* org.apache.lucene.document
	* org.apache.lucene.index
	* org.apache.lucene.search
	* org.apache.lucene.store
	* org.apache.lucene.util
	* org.apache.solr.client.solrj
	* org.apache.solr.client.solrj.embedded
	* org.apache.solr.client.solrj.response
	* org.apache.solr.cloud
	* org.apache.solr.common
	* org.apache.solr.common.cloud
	* org.apache.solr.common.params
	* org.apache.solr.common.util
	* org.apache.solr.handler.admin
	* org.apache.solr.handler.component
	* org.apache.solr.logging
	* org.apache.solr.logging.jul
	* org.apache.solr.request
	* org.apache.solr.response
	* org.apache.solr.response.transform
	* org.apache.solr.schema
	* org.apache.solr.search
	* org.apache.solr.spelling
	* org.apache.solr.update
	* org.apache.solr.update.processor
	* org.apache.solr.util
	* org.apache.solr.util.plugin
* __slf4j-api - slf4j.api (1.6.1)__
	* org.slf4j
* __Apache Commons IO Bundle - org.apache.commons.io (1.4)__
	* org.apache.commons.io
* __Commons Lang - org.apache.commons.lang (2.5)__
	* org.apache.commons.lang
* __Apache Felix Declarative Services - org.apache.felix.scr (1.6.0)__
	* org.osgi.service.component
* __Guava: Google Core Libraries for Java 1.5 - com.google.guava (10.0.0)__
	* com.google.common.collect
## Embedded JARs
* opencsv-2.3.jar