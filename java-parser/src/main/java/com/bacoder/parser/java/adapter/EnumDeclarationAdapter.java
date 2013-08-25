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
import com.bacoder.parser.java.api.ClassMemberDeclaration;
import com.bacoder.parser.java.api.EnumConstant;
import com.bacoder.parser.java.api.EnumDeclaration;
import com.bacoder.parser.java.api.Type;
import com.google.common.base.Function;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ClassBodyDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.EnumBodyDeclarationsContext;
import com.srctran.backend.parser.java.JavaParser.EnumConstantContext;
import com.srctran.backend.parser.java.JavaParser.EnumConstantsContext;
import com.srctran.backend.parser.java.JavaParser.EnumDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;
import com.srctran.backend.parser.java.JavaParser.TypeListContext;

public class EnumDeclarationAdapter extends JavaAdapter<EnumDeclarationContext, EnumDeclaration> {

  public EnumDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public EnumDeclaration adapt(EnumDeclarationContext context) {
    EnumDeclaration enumDeclaration = createData(context);

    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      enumDeclaration.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    TypeListContext typeListContext = getChild(context, TypeListContext.class);
    if (typeListContext != null) {
      List<Type> implementsTypes =
          transform(typeListContext, TypeContext.class, new Function<TypeContext, Type>() {
            @Override
            public Type apply(TypeContext context) {
              return getAdapter(TypeAdapter.class).adapt(context);
            }
          });
      enumDeclaration.setImplementsTypes(implementsTypes);
    }

    EnumConstantsContext enumConstantsContext = getChild(context, EnumConstantsContext.class);
    if (enumConstantsContext != null) {
      List<EnumConstant> constants =
          transform(enumConstantsContext, EnumConstantContext.class,
              new Function<EnumConstantContext, EnumConstant>() {
                @Override
                public EnumConstant apply(EnumConstantContext context) {
                  return getAdapter(EnumConstantAdapter.class).adapt(context);
                }
              });
      enumDeclaration.setConstants(constants);
    }

    EnumBodyDeclarationsContext enumBodyDeclarationsContext =
        getChild(context, EnumBodyDeclarationsContext.class);
    if (enumBodyDeclarationsContext != null) {
      List<ClassMemberDeclaration> memberDeclarations =
          transform(enumBodyDeclarationsContext, ClassBodyDeclarationContext.class,
              new Function<ClassBodyDeclarationContext, ClassMemberDeclaration>() {
                @Override
                public ClassMemberDeclaration apply(ClassBodyDeclarationContext context) {
                  return getAdapter(ClassBodyDeclarationAdapter.class).adapt(context);
                }
              });
      enumDeclaration.setMemberDeclarations(memberDeclarations);
    }

    return enumDeclaration;
  }
}
