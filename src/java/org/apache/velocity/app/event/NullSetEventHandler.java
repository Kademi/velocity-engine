package org.apache.velocity.app.event;

/*
 * Copyright 2001,2004 The Apache Software Foundation.
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

/**
 *  Event handler : lets an app approve / veto
 *  writing a log message when RHS of #set() is null.
 *
 * @author <a href="mailto:geirm@optonline.net">Geir Magnusson Jr.</a>
 * @version $Id: NullSetEventHandler.java,v 1.2.12.1 2004/03/03 23:22:53 geirm Exp $
 */
public interface NullSetEventHandler extends EventHandler
{
    /**
     *  Called when the RHS of a #set() is null, which will result
     *  in a null LHS.
     *
     *  @param lhs  reference literal of left-hand-side of set statement
     *  @param rhs  reference literal of right-hand-side of set statement
     *  @return true if log message should be written, false otherwise
     */
    public boolean shouldLogOnNullSet( String lhs, String rhs );
}
