/**
 * Copyright 2013 Huining (Thomas) Feng (tfeng@berkeley.edu)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
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
package com.bacoder.parser.java.adapter;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.NameValuePair;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ElementValueContext;
import com.srctran.backend.parser.java.JavaParser.ElementValuePairContext;

public class ElementValuePairAdapter
    extends JavaAdapter<ElementValuePairContext, NameValuePair>{

  public ElementValuePairAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public NameValuePair adapt(ElementValuePairContext context) {
    NameValuePair annotationValuePair = createData(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      annotationValuePair.setIdentifier(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    ElementValueContext elementValueContext = getChild(context, ElementValueContext.class);
    if (elementValueContext != null) {
      annotationValuePair.setValue(
          getAdapter(ElementValueAdapter.class).adapt(elementValueContext));
    }

    return annotationValuePair;
  }
}
