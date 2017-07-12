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
package org.apache.velocity.test.filtersmanager;

import java.util.ArrayList;
import jdk.nashorn.api.scripting.ClassFilter;

/**
 *
 * @author Mohammed F. Ouda
 */
public class FiltersManager implements org.apache.velocity.runtime.FiltersManager {

    private ArrayList<ClassFilter> classFilters;

    public FiltersManager() {
        classFilters = new ArrayList<ClassFilter>();

        this.registerClassFilter(new ClassFilterTest());
    }

    public void registerClassFilter(ClassFilter filter) {
        classFilters.add(filter);
    }

    public void deregisterClassFilter(ClassFilter filter) {
        classFilters.remove(filter);
    }

    public boolean exposeToScripts(String classNameString) {
        return this.classFilters.get(0).exposeToScripts(classNameString);
    }

}
