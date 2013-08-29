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

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.ClassOrInterfaceType;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ClassOrInterfaceTypeContext;
import com.srctran.backend.parser.java.JavaParser.TypeArgumentsContext;

public class ClassOrInterfaceTypeAdapter
    extends JavaAdapter<ClassOrInterfaceTypeContext, ClassOrInterfaceType> {

  public ClassOrInterfaceTypeAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public ClassOrInterfaceType adapt(ClassOrInterfaceTypeContext context) {
    ClassOrInterfaceType classOrInterfaceType = null;

    for (ParseTree child : context.children) {
      if (child instanceof TerminalNode
          && ((TerminalNode) child).getSymbol().getType() == JavaParser.Identifier) {
        ClassOrInterfaceType newClassOrInterfaceType =
            createData(context, child, ClassOrInterfaceType.class);
        newClassOrInterfaceType.setIdentifier(
            getAdapter(IdentifierAdapter.class).adapt((TerminalNode) child));
        newClassOrInterfaceType.setScope(classOrInterfaceType);
        classOrInterfaceType = newClassOrInterfaceType;
      } else if (child instanceof TypeArgumentsContext && classOrInterfaceType != null) {
        setNodeAttributes(classOrInterfaceType, context, child);
        classOrInterfaceType.setTypeArguments(
            getAdapter(TypeArgumentsAdapter.class).adapt((TypeArgumentsContext) child));
      }
    }

    return classOrInterfaceType;
  }
}
