package org.apache.velocity.runtime.parser.node;

/*
 * Copyright 2000-2004 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the "License")
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.runtime.parser.Parser;
import org.apache.velocity.runtime.parser.ParserVisitor;

/**
 *
 */
public class ASTFalse extends SimpleNode
{
    private static Boolean value = Boolean.FALSE;

    /**
     * @param id
     */
    public ASTFalse(int id)
    {
        super(id);
    }

    /**
     * @param p
     * @param id
     */
    public ASTFalse(Parser p, int id)
    {
        super(p, id);
    }

    /**
     * @see org.apache.velocity.runtime.parser.node.SimpleNode#jjtAccept(org.apache.velocity.runtime.parser.ParserVisitor, java.lang.Object)
     */
    public Object jjtAccept(ParserVisitor visitor, Object data)
    {
        return visitor.visit(this, data);
    }

    /**
     * @see org.apache.velocity.runtime.parser.node.SimpleNode#evaluate(org.apache.velocity.context.InternalContextAdapter)
     */
    public boolean evaluate( InternalContextAdapter context)
    {
        return false;
    }

    /**
     * @see org.apache.velocity.runtime.parser.node.SimpleNode#value(org.apache.velocity.context.InternalContextAdapter)
     */
    public Object value( InternalContextAdapter context)
    {
        return value;
    }
}
