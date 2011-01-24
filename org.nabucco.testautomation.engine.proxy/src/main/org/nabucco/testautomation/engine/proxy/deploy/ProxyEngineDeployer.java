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
package org.nabucco.testautomation.engine.proxy.deploy;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;
import java.util.jar.JarFile;
import java.util.jar.Manifest;

import org.nabucco.testautomation.engine.base.util.FileUtils;
import org.nabucco.testautomation.engine.base.util.JarUtils;
import org.nabucco.testautomation.engine.proxy.ProxyEngine;
import org.nabucco.testautomation.engine.proxy.ProxyEngineFactory;
import org.nabucco.testautomation.engine.proxy.deploy.ProxySupport;
import org.nabucco.testautomation.engine.proxy.exception.ProxyDeploymentException;


/**
 * ProxyEngineDeployer
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public class ProxyEngineDeployer implements ProxySupport {

	private File proxyJar;

	private JarFile jarFile;

	private String factoryName;
	
	private String proxyEngineName;
	
	private File deployPath;

	private ProxyEngine proxy;

	private ProxyClassLoader proxyClassloader;
	
	private List<File> libraries;

	public ProxyEngineDeployer(File proxyJar) throws ProxyDeploymentException {
		this.proxyJar = proxyJar;
		loadJar();
	}
	
	public String getProxyEngineName() {
		return proxyEngineName;
	}
	
	public File getDeployPath() {
		return deployPath;
	}
	
	public ProxyClassLoader getProxyClassloader() {
		return proxyClassloader;
	}

	private void loadJar() throws ProxyDeploymentException {
		try {
			// Load the .jar
			URL url = FileUtils.toURL(proxyJar);
			proxyClassloader = new ProxyClassLoader(new URL[] { url });

			// validate the .jar and its manifest
			jarFile = new JarFile(proxyJar);
			Manifest manifest = jarFile.getManifest();

			if (manifest == null) {
				throw new ProxyDeploymentException("No manifest found");
			}

			Attributes attr = manifest.getMainAttributes();
			
			if (attr != null) {
				proxyEngineName = attr.getValue("ProxyEngineName");
				factoryName = attr.getValue("ProxyEngineFactory");
			} else {
				throw new ProxyDeploymentException("Invalid jar-file -> Check manifest");
			}
		} catch (MalformedURLException ex) {
			throw new ProxyDeploymentException(ex);
		} catch (IOException ex) {
			throw new ProxyDeploymentException(ex);
		}
	}
	
	private File createDeployPath() {
		File destPath = FileUtils.createPath(proxyJar.getParent()
				+ File.separator + proxyJar.getName() + "_TMP");
		return destPath;
	}

	private void extractLibAndConf() throws ProxyDeploymentException {
		try {
			JarUtils.extractFiles(proxyJar, deployPath,
					new FileFilter() {

						@Override
						public boolean accept(File file) {
							if (file == null) {
								return false;
							}
							String name = file.toString();
							return name.startsWith(LIB_ROOT_PATH)
									|| name.startsWith(CONF_ROOT_PATH);
						}
					});
		} catch (IOException ex) {
			throw new ProxyDeploymentException(
					"Error while extracting libraries", ex);
		}
	}
	
	private void loadLibraries() {
		File tempLibPath = new File(deployPath + File.separator + ProxySupport.LIB_ROOT_PATH);
		
		if (tempLibPath.exists()) {
			this.libraries = new ArrayList<File>();
			FileUtils.addFilesToList(tempLibPath, libraries, true);
			
			for (File file : libraries) {
				try {
					URL url = FileUtils.toURL(file);
					proxyClassloader.addURL(url);
				} catch (MalformedURLException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public ProxyEngine deploy() throws ProxyDeploymentException {
		try {
			deployPath = createDeployPath();
			extractLibAndConf();
			loadLibraries();

			// Deployment-Lifecycle
			ProxyEngineFactory proxyFactory = (ProxyEngineFactory) proxyClassloader
					.loadClass(factoryName).newInstance();
			proxy = proxyFactory.getProxyEngineInstance();
			proxy.initializeProxy(this);
			// ---

			return proxy;
		} catch (ClassCastException ex) {
			throw new ProxyDeploymentException("Could not cast class "
					+ factoryName + " into ProxyEngineFactory", ex);
		} catch (InstantiationException ex) {
			throw new ProxyDeploymentException("Could not instantiate class "
					+ factoryName, ex);
		} catch (IllegalAccessException ex) {
			throw new ProxyDeploymentException("Could not access class "
					+ factoryName, ex);
		} catch (ClassNotFoundException ex) {
			throw new ProxyDeploymentException("Could not find class "
					+ factoryName, ex);
		}
	}

	public void undeploy() throws ProxyDeploymentException {
		// Undeployment-Lifecycle
		proxy.stopProxy();
		proxy.unConfigureProxy();
		FileUtils.delete(deployPath);
		// ---
	}

	@Override
	public List<File> getRuntimeLibs() {
		return this.libraries;
	}

	@Override
	public File getProxyJar() {
		return this.proxyJar;
	}

}
