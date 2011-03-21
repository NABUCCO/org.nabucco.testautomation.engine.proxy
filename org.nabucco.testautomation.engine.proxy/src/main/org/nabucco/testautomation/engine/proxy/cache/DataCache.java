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
package org.nabucco.testautomation.engine.proxy.cache;

import java.util.HashMap;
import java.util.Map;

import org.nabucco.framework.base.facade.datatype.Data;
import org.nabucco.framework.base.facade.datatype.Identifier;

/**
 * DataCache
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public final class DataCache {

	private static DataCache instance;
	
	private final Map<Identifier, Data> cache = new HashMap<Identifier, Data>();
	
	private DataCache() {}
	
	/**
	 * 
	 * @return
	 */
	public static synchronized DataCache getInstance() {
		
		if (instance == null) {
			instance = new DataCache();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param id
	 * @param data
	 */
	public synchronized void put(Identifier id, Data data) {
		this.cache.put(id, data);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public synchronized Data get(Identifier id) {
		return this.cache.get(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public synchronized Data remove(Identifier id) {
		return this.cache.remove(id);
	}	
	
}
