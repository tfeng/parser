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

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.InterfaceBodyContext;
import com.bacoder.parser.java.JavaParser.InterfaceBodyDeclarationContext;
import com.bacoder.parser.java.JavaParser.InterfaceDeclarationContext;
import com.bacoder.parser.java.JavaParser.TypeListContext;
import com.bacoder.parser.java.JavaParser.TypeParametersContext;
import com.bacoder.parser.java.api.InterfaceDeclaration;
import com.bacoder.parser.java.api.InterfaceMemberDeclaration;
import com.google.common.base.Function;

public class InterfaceDeclarationAdapter
    extends JavaAdapter<InterfaceDeclarationContext, InterfaceDeclaration> {

  public InterfaceDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public InterfaceDeclaration adapt(InterfaceDeclarationContext context) {
    InterfaceDeclaration interfaceDeclaration = createNode(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      interfaceDeclaration.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    TypeParametersContext typeParametersContext = getChild(context, TypeParametersContext.class);
    if (typeParametersContext != null) {
      interfaceDeclaration.setTypeParameters(
          getAdapter(TypeParametersAdapter.class).adapt(typeParametersContext));
    }

    TypeListContext typeListContext = getChild(context, TypeListContext.class);
    if (typeListContext != null) {
      interfaceDeclaration.setExtendsTypes(
          getAdapter(TypeListAdapter.class).adapt(typeListContext));
    }

    InterfaceBodyContext interfaceBodyContext = getChild(context, InterfaceBodyContext.class);
    if (interfaceBodyContext != null) {
      List<InterfaceMemberDeclaration> memberDeclarations =
          transform(interfaceBodyContext, InterfaceBodyDeclarationContext.class,
              new Function<InterfaceBodyDeclarationContext, InterfaceMemberDeclaration>() {
                @Override
                public InterfaceMemberDeclaration apply(InterfaceBodyDeclarationContext context) {
                  return getAdapter(InterfaceBodyDeclarationAdapter.class).adapt(context);
                }
              });
      interfaceDeclaration.setMemberDeclarations(memberDeclarations);
    }

    return interfaceDeclaration;
  }
}
