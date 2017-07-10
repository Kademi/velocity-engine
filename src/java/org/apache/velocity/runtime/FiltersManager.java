/*
 * Copyright 2017 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.velocity.runtime;

import jdk.nashorn.api.scripting.ClassFilter;

/**
 *
 * This interface works on providing Velocity with the ability of creating
 * filter for classes which are going to be invoked from the HTML and execute
 * during the process of rendering velocity template
 *
 * All what you need to start using this interface and filtering velocity is to
 * add an attribute to your project before initializing the engine. So velocity
 * is going to filter the ability of reaching any of the classes depends on what
 * are you going to implement at (class FiltersManagerClass implements
 * FiltersManager) in your project.
 *
 * NOTE: anytime exposeToScripts() returns false, that means velocity is going
 * to reject the access to this class through HTML developers.
 *
 *
 * e.g.: VelocityEngine ve = new VelocityEngine();
 * ve.addProperty("velocity.filtersmanager",
 * "com.velocity.test.FiltersManager"); ve.init();
 */
public interface FiltersManager {

    void registerClassFilter(ClassFilter filter);

    void deregisterClassFilter(ClassFilter filter);

    boolean exposeToScripts(String classNameString);
}
