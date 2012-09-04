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
package org.nabucco.testautomation.engine.proxy.base;

import java.util.List;

import org.nabucco.testautomation.engine.base.context.TestContext;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.SubEngine;
import org.nabucco.testautomation.engine.proxy.SubEngineActionType;
import org.nabucco.testautomation.engine.proxy.SubEngineOperationType;
import org.nabucco.testautomation.engine.proxy.exception.SubEngineException;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.result.facade.datatype.ActionResponse;
import org.nabucco.testautomation.result.facade.datatype.TestResult;
import org.nabucco.testautomation.result.facade.datatype.status.ActionStatusType;
import org.nabucco.testautomation.script.facade.datatype.metadata.Metadata;

/**
 * AbstractSubEngine
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public abstract class AbstractSubEngine implements SubEngine {

    private static final long serialVersionUID = 1L;

    /**
     * Constructes a new instance for current proxy-session.
     */
    protected AbstractSubEngine() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ActionResponse execute(SubEngineOperationType operationType, SubEngineActionType actionType, 
            List<Metadata> metadataList, PropertyList propertyList, TestContext context)
            throws SubEngineException {

        // execute callback-operation
        return executeSubEngineOperation(operationType, actionType, metadataList, propertyList,
                context);
    }

    /**
     * Creates an error result object of type {@link TestResult} containing the given error
     * message and context.
     * 
     * @param message
     *            the error message
     * @param context
     *            the context
     * @return an error result
     */
    protected ActionResponse createErrorResult(String message, TestContext context) {
        ActionResponse response = TestResultHelper.createActionResponse();
        response.setActionStatus(ActionStatusType.FAILED);
        response.setErrorMessage(message);
        return response;
    }

    /**
     * Callback-operation to execute the given SubEngineAction on the specified engineOperation.
     * 
     * @param actionType
     *            the action to be executed
     * @param engineOperation
     *            the operation to be called
     * @param MetadataList
     *            the list of metadata references
     * @param propertyList
     *            the list properties
     * @param context
     *            the context
     * @return the result object of the execution
     * @throws SubEngineException
     *             throw, if an error occurs during the execution
     */
    protected abstract ActionResponse executeSubEngineOperation(
            SubEngineOperationType operationType, SubEngineActionType actionType, List<Metadata> metadataList,
            PropertyList propertyList, TestContext context) throws SubEngineException;

}
