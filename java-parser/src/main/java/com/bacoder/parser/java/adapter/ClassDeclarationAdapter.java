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
import com.bacoder.parser.java.api.ClassDeclaration;
import com.bacoder.parser.java.api.Identifier;
import com.google.common.base.Function;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ClassBodyContext;
import com.srctran.backend.parser.java.JavaParser.ClassDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;
import com.srctran.backend.parser.java.JavaParser.TypeListContext;
import com.srctran.backend.parser.java.JavaParser.TypeParametersContext;

public class ClassDeclarationAdapter
    extends JavaAdapter<ClassDeclarationContext, ClassDeclaration> {

  public ClassDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public ClassDeclaration adapt(ClassDeclarationContext context) {
    ClassDeclaration classDeclaration = createData(context);

    Identifier name =
        transformOne(context, TerminalNode.class, new Function<TerminalNode, Identifier>() {
          @Override
          public Identifier apply(TerminalNode node) {
            if (node.getSymbol().getType() == JavaParser.Identifier) {
              return getAdapter(IdentifierAdapter.class).adapt(node);
            } else {
              return null;
            }
          }
        });
    classDeclaration.setName(name);

    TypeParametersContext typeParametersContext = getChild(context, TypeParametersContext.class);
    if (typeParametersContext != null) {
      classDeclaration.setTypeParameters(
          getAdapter(TypeParametersAdapter.class).adapt(typeParametersContext));
    }

    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      classDeclaration.setExtendsType(getAdapter(TypeAdapter.class).adapt(typeContext));
    }

    TypeListContext typeListContext = getChild(context, TypeListContext.class);
    if (typeListContext != null) {
      classDeclaration.setImplementsTypes(getAdapter(TypeListAdapter.class).adapt(typeListContext));
    }

    ClassBodyContext classBodyContext = getChild(context, ClassBodyContext.class);
    if (classBodyContext != null) {
      classDeclaration.setMemberDeclarations(
          getAdapter(ClassBodyAdapter.class).adapt(classBodyContext));
    }

    return classDeclaration;
  }
}
