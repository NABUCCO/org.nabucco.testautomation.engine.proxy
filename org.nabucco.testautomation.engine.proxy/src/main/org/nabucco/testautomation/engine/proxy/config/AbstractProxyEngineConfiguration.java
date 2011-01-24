/*
* Copyright 2010 PRODYNA AG
*
* Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
* you may not use this file except in compliance with the License.
* You may obtain a copy of the License at
*
* http://www.opensource.org/licenses/eclipse-1.0.php or
* http://www.nabucco-source.org/nabucco-license.html
*
* Unless required by applicable law or agreed to in writing, software
* distributed under the License is distributed on an "AS IS" BASIS,
* WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
* See the License for the specific language governing permissions and
* limitations under the License.
*/
package org.nabucco.testautomation.engine.proxy.config;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.testautomation.facade.datatype.engine.proxy.ConfigurationProperty;
import org.nabucco.testautomation.facade.datatype.engine.proxy.ProxyConfiguration;

/**
 * AbstractProxyEngineConfiguration
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public abstract class AbstractProxyEngineConfiguration {

	private ClassLoader classloader;
	
	private ProxyConfiguration configuration;
	
	private Map<String, String> configMap;
	
	protected AbstractProxyEngineConfiguration(ClassLoader classloader, ProxyConfiguration configuration) {
		this.classloader = classloader;
		this.configuration = configuration;
		initConfigMap();
	}
	
	public ClassLoader getClassloader() {
		return this.classloader;
	}
	
	public ProxyConfiguration getProxyConfiguration() {
		return this.configuration;
	}
	
	protected String getConfigurationValue(String key) {
		return this.configMap.get(key);
	}
	
	private void initConfigMap() {
		this.configMap = new HashMap<String, String>(); 
		
		for (ConfigurationProperty prop : this.configuration.getConfigurationProperties()) {
			String key = prop.getName() != null ? prop.getName().getValue() : null;
			String value = prop.getValue() != null ? prop.getValue().getValue() : null;
			this.configMap.put(key, value);
		}
	}
	
}