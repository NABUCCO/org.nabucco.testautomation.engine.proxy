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

import java.util.Date;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.ProxyCommand;
import org.nabucco.testautomation.property.facade.datatype.PropertyList;
import org.nabucco.testautomation.property.facade.datatype.base.Property;
import org.nabucco.testautomation.property.facade.datatype.base.PropertyContainer;
import org.nabucco.testautomation.result.facade.datatype.trace.ActionTrace;
import org.nabucco.testautomation.result.facade.datatype.trace.MessageTrace;

/**
 * AbstractProxyCommand
 * 
 * @author Steffen Schmidt, PRODYNA AG
 */
public abstract class AbstractProxyCommand implements ProxyCommand {

	private long start;

	private long stop;

	private String rq;

	private String rs;

	private Exception ex;
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public ActionTrace getActionTrace() {
		MessageTrace trace = TestResultHelper.createMessageTrace();
		long duration = this.stop - this.start;
		trace.setStartTime(new Date(this.start));
		trace.setEndTime(new Date(this.stop > 0 ? this.stop : System.currentTimeMillis()));
		trace.setDuration(duration >= 0 ? duration : System.currentTimeMillis() - this.start);
		trace.setRequest(this.rq);
		trace.setResponse(this.rs);
		trace.setStackTrace(TestResultHelper.getStackTrace(this.ex));
		return trace;
	}
	
	/**
	 * Starts the stop-watch for tracing.
	 */
	protected void start() {
		this.start = System.currentTimeMillis();
	}

	/**
	 * Stops the stop-watch for tracing.
	 */
	protected void stop() {
		this.stop = System.currentTimeMillis();
	}
	
	/**
	 * Gets the stopped startTime.
	 * 
	 * @return the startTime
	 */
	protected long getStart() {
		return this.start;
	}
	
	/**
	 * Gets the stopped stopTime.
	 * 
	 * @return the stopTime
	 */
	protected long getStop() {
		return this.stop;
	}
	
	/**
	 * Sets an (external) Exception, that was caught during the execution of the command.
	 * This exception will be set in to ActionTrace returned by getActionTrace().
	 * 
	 * @param ex the exception to set
	 */
	protected void setException(Exception ex) {
		this.ex = ex;
	}
	
	/**
	 * Gets the exception.
	 * 
	 * @return the set exception or null, if no one was set
	 */
	protected Exception getException() {
		return this.ex;
	}
	
	/**
	 * Gets the request-message as it was sent to the external system.
	 * 
	 * @return the request-message as string or null, if no one was set
	 */
	protected String getRequest() {
		return this.rq;
	}

	/**
	 * Gets the response-message as it was received from the external system.
	 * 
	 * @return the response-message as string or null, if no one was set
	 */
	protected String getResponse() {
		return this.rs;
	}
	
	/**
	 * Sets the request-message that was sent to the external system.
	 * 
	 * @param rq the request-message to set
	 */
	protected void setRequest(String rq) {
		this.rq = rq;
	}

	/**
	 * Sets the response-message that was received from the external system.
	 * 
	 * @param rs the response-message to set
	 */
	protected void setResponse(String rs) {
		this.rs = rs;
	}

	/**
	 * Adds the given Property to the end of the given PropertyList.
	 * 
	 * @param property the property to add
	 * @param list the PropertyList to add the Property to
	 */
	protected void add(Property property, PropertyList list) {
		PropertyContainer container = new PropertyContainer();
		container.setDatatypeState(DatatypeState.INITIALIZED);
		container.setOrderIndex(list.getPropertyList().size());
		container.setProperty(property);
		list.getPropertyList().add(container);
	}
	
	/**
	 * Logs a message on logging-level INFO
	 * 
	 * @param msg the message to log
	 */
	protected abstract void info(String msg);

	/**
	 * Logs a message on logging-level DEBUG
	 * 
	 * @param msg the message to log
	 */
	protected abstract void debug(String msg);

	/**
	 * Logs a message on logging-level ERROR
	 * 
	 * @param msg the message to log
	 */
	protected abstract void error(String msg);
	
	/**
	 * Logs a message on logging-level WARNING
	 * 
	 * @param msg the message to log
	 */
	protected abstract void warning(String msg);
	
}
