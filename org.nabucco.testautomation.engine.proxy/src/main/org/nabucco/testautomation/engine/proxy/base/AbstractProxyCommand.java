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

import java.util.Date;

import org.nabucco.framework.base.facade.datatype.DatatypeState;
import org.nabucco.testautomation.engine.base.util.TestResultHelper;
import org.nabucco.testautomation.engine.proxy.ProxyCommand;

import org.nabucco.testautomation.facade.datatype.property.PropertyList;
import org.nabucco.testautomation.facade.datatype.property.base.Property;
import org.nabucco.testautomation.facade.datatype.property.base.PropertyContainer;
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
	
	protected void start() {
		this.start = System.currentTimeMillis();
	}

	protected void stop() {
		this.stop = System.currentTimeMillis();
	}
	
	protected long getStart() {
		return this.start;
	}
	
	protected long getStop() {
		return this.stop;
	}
	
	protected Exception getException() {
		return this.ex;
	}
	
	protected String getRequest() {
		return this.rq;
	}

	protected String getResponse() {
		return this.rs;
	}
	
	protected void setRequest(String rq) {
		this.rq = rq;
	}

	protected void setResponse(String rs) {
		this.rs = rs;
	}

	protected void setException(Exception ex) {
		this.ex = ex;
	}
	
	protected void add(Property property, PropertyList list) {
		PropertyContainer container = new PropertyContainer();
		container.setDatatypeState(DatatypeState.INITIALIZED);
		container.setOrderIndex(list.getPropertyList().size());
		container.setProperty(property);
		list.getPropertyList().add(container);
	}
	
	protected abstract void info(String msg);

	protected abstract void debug(String msg);

	protected abstract void error(String msg);
	
}
