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

import org.nabucco.testautomation.engine.proxy.exception.SubEngineException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.trace.ActionTrace;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

/**
 * ProxyCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public interface ProxyCommand {

	/**
	 * Executes a Command on the specified {@link Metadata} with the 
	 * specified List of parameters.
	 * 
	 * @param metadata the Metadata to execute the command on
	 * @param properties the list of parameters
	 * @return a list containg the result Properties
	 * @throws SubEngineException thrown, if an error occurs during execution
	 */
	public PropertyList execute(Metadata metadata, PropertyList properties) throws SubEngineException;
	
	/**
	 * Returns an {@link ActionTrace} produced during the last execution.
	 * 
	 * @return the ActionTrace
	 */
	public ActionTrace getActionTrace();
	
}
