/*
 * Copyright 2012 PRODYNA AG
 *
 * Licensed under the Eclipse Public License (EPL), Version 1.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.opensource.org/licenses/eclipse-1.0.php or
 * http://www.nabucco.org/License.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.nabucco.testautomation.engine.proxy;

import org.nabucco.testautomation.engine.proxy.deploy.ProxySupport;
import org.nabucco.testautomation.engine.proxy.exception.ProxyConfigurationException;
import org.nabucco.testautomation.settings.facade.datatype.engine.SubEngineType;
import org.nabucco.testautomation.settings.facade.datatype.engine.proxy.ProxyConfiguration;

/**
 * ProxyEngine
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public interface ProxyEngine {

    /**
     * Initializes the proxy and its proxy engines
     */
    public void initializeProxy(ProxySupport proxySupport) throws ProxyConfigurationException;

    /**
     * Configures the proxy by the given configuration ConfigurationProperties
     * 
     * @param configuration
     */
    public void configureProxy(ProxyConfiguration configuration) throws ProxyConfigurationException;

    /**
     * Starting the proxy, making the proxy 'ready to use'
     */
    public void startProxy() throws ProxyConfigurationException;

    /**
     * Stopping the proxy, making the proxy 'not usable'
     */
    public void stopProxy() throws ProxyConfigurationException;

    /**
     * Clears the proxy configuration. After this the proxy is ready to be configured with a new
     * configuration.
     */
    public void unConfigureProxy() throws ProxyConfigurationException;

    /**
     * Gets the configured and started proxy-specific SubEngine.
     * 
     * @return the proxy-specific instance of SubEngine
     */
    public SubEngine getSubEngine();

    /**
     * Gets the type of the SubEngine
     * 
     * @return the SubEngineType
     */
    public SubEngineType getSubEngineType();

}
