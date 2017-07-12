package org.apache.velocity.test.filtersmanager;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.    
 */
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.test.BaseTestCase;

/**
 * This class tests the effects of filters manager over velocity engine 
 * and other classes when:
 * - its added to the velocity.
 * - its not added to the velocity.
 * - its added and used in the velocity.
 *      - verifies that we can not call the class.
 *      - verifies that we can not call a getter from the class.
 *      - verifies that we can not call a setter from the class.
 *      - verifies that filters manager will not disable velocity from calling
 *        the parent of filtered class.
 *      - verifies that we can not call childs of filtered class.
 *      - insure that passing Class.class to the template:
 *          - will not enable velocity from accessing filtered classes through
 *            Class.forName and make new instances.
 *          - will not enable velocity from accessing child classes of
 *            filtered ones through Class.forName and make new instances.
 *          - will not disable velocity from accessing parent classes of
 *            filtered ones through Class.forName and make new instances.
 * 
 *  NOTE: class attributes which has not getters/setters 
 *        are not accessible from velocity by default.
 * 
 *  ---#10 test cases---
 */
public class FiltersManagerTestCase extends BaseTestCase {
    
    /**
     * Directory which has *.vm & *.rsc files.
     * *.vm: templates files.
     * *.res: running tests results.
     */
    private static final String RESULTS_DIR = TEST_RESULT_DIR + "/filtersmanager/";
    
    /**
     * Property of filters manager in velocity engine.
     */
    private static final String addPropertyString = "velocity.filtersmanager";
    
    /**
     * Our testing class path for filters manager.
     * Which implements org.apache.velocity.runtime.FiltersManager
     */
    private static final String addPropertyObject = "org.apache.velocity.test.filtersmanager.FiltersManager";
    
    /**
     * Pushed and displayed title in all templates.
     */
    private static final String testCaseTitle = "Filters Manager Test Case";
    
    /**
     * Super class for testing use cases, pushed to the templates.
     */
    private Car testingCar = new Car("X6", "BMW", 2017, "RED");
    
    /**
     * Child class of Car for testing use cases, pushed to the templates.
     * 
     * @extends Car
     */
    private SuperCar testingCarChild = new SuperCar("X6-Child", "BMW-Child", 2017, "RED-Child");

    /**
     * Filters Manager Constructor
     * @param name 
     */
    public FiltersManagerTestCase(String name) {
        super(name);       
    }
    
    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * Without doing any filtering.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActive() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         */
        velocityContext.put("test-case", testCaseTitle);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }
    
    /**
     * In this case we are going to check the behaviour of velocity without
     * implementing filters manager to the engine.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-inActive-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-inActive-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-inActive-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMinActive() throws Exception { 
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-inActive-TestCase";
        
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         */
        velocityContext.put("test-case", testCaseTitle);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }
    
    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that 
     * we can not call it.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClass() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }

    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that 
     * we can not call its getters.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Getter-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Getter-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Getter-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassGetter() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Getter-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }

    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that 
     * we can not call its setters.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Setter-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Setter-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Setter-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassSetter() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Setter-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }

    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that 
     * filters manager do not disable velocity from reaching 
     * parent of filtered class.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Parent-Instances-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Parent-Instances-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Parent-Instances-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassParentInstances() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Parent-Instances-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject + "2");        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);
        velocityContext.put("testing-car-child", testingCarChild);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }

    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that filters manager 
     * prevent velocity from reaching its childs or make instances of them.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Child-Instances-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Child-Instances-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Child-Instances-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassChildInstances() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Child-Instances-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         * 
         * Parent Class: testingCar
         * Child Class: testingCarChild
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);
        velocityContext.put("testing-car-child", testingCarChild);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }

    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that 
     * we can not create a new instance of it through Class.class if
     * Class.class was passed to the template.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Class-Instance-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Class-Instance-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Class-Instance-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassClassInstances() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Class-Instances-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        
                
        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);
        velocityContext.put("Class", Class.class);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }
    
    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that velocity will not 
     * access child classes of filtered ones through Class.forName 
     * and make new instances.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Class-Childs-Instances-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Class-Childs-Instances-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Class-Childs-Instances-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassClassChildsInstances() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Class-Childs-Instances-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject);        

        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);
        velocityContext.put("Class", Class.class);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }
    
    /**
     * In this case we are going to check the behaviour of velocity after
     * implementing filters manager to the engine as a property.
     * In addition to filtering a class and verifies that velocity will not 
     * disable velocity from accessing the parent of filtered classes
     * through Class.forNam and make new instances.
     * 
     * Related files:
     * - Which is the template:
     * \build\filtersmanager\FM-Active-Used-Class-Class-Parent-Instances-TestCase.vm
     * - Which is the rendering result:
     * \build\filtersmanager\FM-Active-Used-Class-Class-Parent-Instances-TestCase.res
     * - Which is the comparable side with *.res file 
     * to insure the result of the test:
     * \test\templates\compare\FM-Active-Used-Class-Class-Parent-Instances-TestCase.cmp
     * 
     * @throws Exception 
     */
    public void testFMActiveUsedClassClassParentInstances() throws Exception {
        /**
         * All of the three used files has the same name, but with different
         * extensions so we generalized the name to make it easier in making
         * other tests.
         */
        String testCaseFileName = "FM-Active-Used-Class-Class-Parent-Instances-TestCase";
        
        /**
         * First: we implements filters manager to the engine before using it.
         */
        engine.addProperty(addPropertyString, addPropertyObject + "2");        

        /**
         * Template file which has the runnable source. 
         * comes with *.vm extension.
         */
        Template template = engine.getTemplate(getFileName(null, RESULTS_DIR + testCaseFileName, TMPL_FILE_EXT));

        /**
         * The final result of rendering the previous template through
         * the engine.
         * It comes into this file which has the same name but *.res extension.
         */
        FileOutputStream fos = new FileOutputStream(getFileName(RESULTS_DIR, testCaseFileName, RESULT_FILE_EXT));

        Writer writer = new BufferedWriter(new OutputStreamWriter(fos));

        VelocityContext velocityContext = new VelocityContext();
        
        /**
         * .put("test-case", ...) is original method of velocity,
         * we use it here to be sure that velocity works correctly.
         * 
         * .put("testing-car", ...) is the class which we are going to filter
         * using filters manager.
         */
        velocityContext.put("test-case", testCaseTitle);
        velocityContext.put("testing-car", testingCar);
        velocityContext.put("Class", Class.class);

        template.merge(velocityContext, writer);
        writer.flush();
        writer.close();
        
        if (!isMatch(RESULTS_DIR, COMPARE_DIR, testCaseFileName, RESULT_FILE_EXT, CMP_FILE_EXT)) {
            fail(testCaseFileName + ": Output incorrect.");
        }
    }
}