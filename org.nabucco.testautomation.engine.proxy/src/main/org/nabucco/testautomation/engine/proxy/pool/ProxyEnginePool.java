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
package org.nabucco.testautomation.engine.proxy.pool;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import org.nabucco.testautomation.engine.base.exception.NBCTestConfigurationException;
import org.nabucco.testautomation.engine.proxy.ProxyEngine;
import org.nabucco.testautomation.settings.facade.datatype.engine.SubEngineType;

/**
 * ProxyEnginePool
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public final class ProxyEnginePool {

	private final ConcurrentMap<SubEngineType, ProxyEngine> proxyEngineMap = new ConcurrentHashMap<SubEngineType, ProxyEngine>();

	public ProxyEnginePool() {
	}

	public final synchronized ProxyEngine getProxyEngine(SubEngineType type)
			throws NBCTestConfigurationException {

		if (!proxyEngineMap.containsKey(type)) {
			throw new NBCTestConfigurationException(
					"No ProxyEngine configured for SubEngineType " + type);
		}
		return proxyEngineMap.get(type);
	}

	public final void addProxyEngine(ProxyEngine proxyEngine) {
		proxyEngineMap.putIfAbsent(proxyEngine.getSubEngineType(), proxyEngine);
	}
	
	public final synchronized Collection<ProxyEngine> getProxyEngines() {
		return Collections.unmodifiableCollection(proxyEngineMap.values());
	}

}
