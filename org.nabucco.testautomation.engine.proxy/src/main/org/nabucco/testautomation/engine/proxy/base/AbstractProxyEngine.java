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
package org.nabucco.testautomation.engine.proxy.base;

import org.nabucco.testautomation.engine.proxy.ProxyEngine;
import org.nabucco.testautomation.engine.proxy.SubEngine;
import org.nabucco.testautomation.engine.proxy.config.ProxyEngineConfiguration;
import org.nabucco.testautomation.engine.proxy.deploy.ProxySupport;
import org.nabucco.testautomation.engine.proxy.exception.ProxyConfigurationException;

import org.nabucco.testautomation.facade.datatype.engine.SubEngineType;
import org.nabucco.testautomation.facade.datatype.engine.proxy.ProxyConfiguration;

/**
 * AbstractProxyEngine
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public abstract class AbstractProxyEngine implements ProxyEngine {

	private boolean configured = false;

	private SubEngine subEngine;

	private SubEngineType subEngineType;

	private ProxySupport proxySupport;

    /**
     * Constructs a new AbstractProxyEngine of the given {@link SubEngineType}.
     * 
     * @param subEngineType
     *            the type of the ProxyEngine
     */
	protected AbstractProxyEngine(SubEngineType subEngineType) {
		this.subEngineType = subEngineType;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public final SubEngine getSubEngine() {
		return subEngine;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public final SubEngineType getSubEngineType() {
		return subEngineType;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public final void configureProxy(ProxyConfiguration configuration)
			throws ProxyConfigurationException {
		Thread.currentThread().setContextClassLoader(getProxySupport().getProxyClassloader());
		ProxyEngineConfiguration proxyConfig = getProxyConfiguration(configuration);
		configure(proxyConfig);
		configured = true;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public final void initializeProxy(ProxySupport proxySupport)
			throws ProxyConfigurationException {
		this.proxySupport = proxySupport;
		initialize();
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public final void startProxy() throws ProxyConfigurationException {

		if (configured) {
			subEngine = start();
		} else {
			throw new ProxyConfigurationException(
					"Proxy not properly configured");
		}
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public final void stopProxy() throws ProxyConfigurationException {
		stop();
		subEngine = null;
	}

	/**
     * {@inheritDoc}
     */
	@Override
	public final void unConfigureProxy() throws ProxyConfigurationException {
		unconfigure();
		subEngine = null;
		configured = false;
	}
	
    /**
     * Gets the {@link ProxySupport} this ProxyEngine was initialized with.
     * 
     * @return the ProxySupport of this ProxyEngine
     */
	protected final ProxySupport getProxySupport() {
		return proxySupport;
	}

    /**
     * Callback-operation to convert the given {@link ConfigurationProperties} into the
     * proxy-specific implementation of {@link ProxyConfiguration}.
     * 
     * @param configProps
     *            the configuration properties
     * @return the proxy-specific implementation
     */
	protected abstract ProxyEngineConfiguration getProxyConfiguration(
			ProxyConfiguration configProps);

    /**
     * Callback-operation for proxy-specific initialization.
     * 
     * @throws ProxyConfigurationException
     *             thrown, if the proxy cannot be initialized
     */
	protected abstract void initialize() throws ProxyConfigurationException;

	/**
	 * Callback-operation for proxy-specific configuration using the given 
	 * {@link ProxyConfiguration} instance.
	 * 
	 * @param config the proxy-specific configuration
	 * @throws ProxyConfigurationException thrown, if the proxy cannot be configured
	 */
	protected abstract void configure(ProxyEngineConfiguration config)
			throws ProxyConfigurationException;

    /**
     * Callback-operation to start the proxy.
     * 
     * @param metadataMap
     *            the MetadataCache for the started proxy-session
     * @return the proxy-specific SubEngine implementation
     * @throws ProxyConfigurationException
     *             thrown, if the proxy cannot be started
     */
	protected abstract SubEngine start()
			throws ProxyConfigurationException;

	/**
	 * Callback-operation to stop the proxy.
	 * 
	 * @throws ProxyConfigurationException
     *             thrown, if the proxy cannot be stopped
	 */
	protected abstract void stop() throws ProxyConfigurationException;

	/**
     * Callback-operation to remove the proxy-specific configuration.
     * 
     * @throws ProxyConfigurationException
     *             thrown, if the proxy cannot be unconfigured
     */
	protected abstract void unconfigure() throws ProxyConfigurationException;

}