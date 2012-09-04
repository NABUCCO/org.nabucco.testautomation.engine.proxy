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

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.proxy.exception.SubEngineException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.ActionResponse;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;
import org.nabucco.testautomation.settings.facade.datatype.engine.SubEngineType;

/**
 * SubEngine
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public interface SubEngine extends Serializable {

    /**
     * Executes the given SubEngineAction on the specified engineOperation.
     * 
     * @param operationType
     *            the operation to be called
     * @param actionType
     *            the action to be executed
     * @param MetadataList
     *            the list of metadata references
     * @param propertyList
     *            the list properties
     * @param context
     *            the context
     * 
     * @return the result object of the execution
     * 
     * @throws SubEngineException
     *             throw, if an error occurs during the execution
     */
    public ActionResponse execute(SubEngineOperationType operationType, SubEngineActionType actionType,
            List<Metadata> MetadataList, PropertyList propertyList, TestContext context) throws SubEngineException;

    /**
     * Gets am Map with all available operations of type {@link SubEngineOperationType} an their
     * string representations, which must match the literal name.
     * 
     * @return a map with all EngineOperationType and their string representations
     */
    public Map<String, SubEngineOperationType> getOperations();

    /**
     * Gets am Map with all available actions of type {@link SubEngineActionType} an their string
     * representations, which must match the literal name.
     * 
     * @return a map with all EngineOperationType and their string representations
     */
    public Map<String, SubEngineActionType> getActions();

    /**
     * Gets the concrete {@link SubEngineType} of this SubEngine instance.
     * 
     * @return the type of this SubEngine instance
     */
    public SubEngineType getType();

}
