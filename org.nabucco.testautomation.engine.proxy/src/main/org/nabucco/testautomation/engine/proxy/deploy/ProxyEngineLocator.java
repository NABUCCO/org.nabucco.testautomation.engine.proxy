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
package org.nabucco.testautomation.engine.proxy.deploy;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * ProxyEngineLocator
 * 
 * @author Steffen Schmidt, PRODYNA AG
 * 
 */
public class ProxyEngineLocator {

    public List<File> locateProxies(String deployPath) {
        return locateProxies(new File(deployPath));
    }

    public List<File> locateProxies(File deployPath) {
        File[] jars = deployPath.listFiles();
        List<File> proxyJars = new ArrayList<File>();

        if (jars != null) {
            for (File file : jars) {
                if (file.isFile()) {
                    // TODO identify proxy jar
                    // JarFile jar = new JarFile(file);
                    proxyJars.add(file);
                }
            }
        }
        return proxyJars;
    }

}
