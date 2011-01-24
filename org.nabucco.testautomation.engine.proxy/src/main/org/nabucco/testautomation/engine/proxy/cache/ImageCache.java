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

import org.nabucco.framework.base.facade.datatype.Identifier;
import org.nabucco.framework.base.facade.datatype.image.ImageData;

/**
 * ImageCache
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public final class ImageCache {

	private static ImageCache instance;
	
	private final Map<Identifier, ImageData> cache = new HashMap<Identifier, ImageData>();
	
	private ImageCache() {}
	
	/**
	 * 
	 * @return
	 */
	public static synchronized ImageCache getInstance() {
		
		if (instance == null) {
			instance = new ImageCache();
		}
		return instance;
	}
	
	/**
	 * 
	 * @param id
	 * @param image
	 */
	public synchronized void put(Identifier id, ImageData image) {
		this.cache.put(id, image);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public synchronized ImageData get(Identifier id) {
		return this.cache.get(id);
	}
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	public synchronized ImageData remove(Identifier id) {
		return this.cache.remove(id);
	}	
	
}
