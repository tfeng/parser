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
import com.bacoder.parser.java.api.ArrayType;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ClassOrInterfaceTypeContext;
import com.srctran.backend.parser.java.JavaParser.PrimitiveTypeContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;

public class TypeAdapter extends JavaAdapter<TypeContext, Type> {

  public TypeAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Type adapt(TypeContext context) {
    Type type = null;
    ParseTree typeNode = context;

    ClassOrInterfaceTypeContext classOrInterfaceTypeContext =
        getChild(context, ClassOrInterfaceTypeContext.class);
    if (classOrInterfaceTypeContext != null) {
      type = getAdapter(ClassOrInterfaceTypeAdapter.class).adapt(classOrInterfaceTypeContext);
      typeNode = classOrInterfaceTypeContext;
    }

    PrimitiveTypeContext primitiveTypeContext = getChild(context, PrimitiveTypeContext.class);
    if (primitiveTypeContext != null) {
      type = getAdapter(PrimitiveTypeAdapter.class).adapt(primitiveTypeContext);
      typeNode = primitiveTypeContext;
    }

    for (ParseTree node : context.children) {
      if (node instanceof TerminalNode
          && ((TerminalNode) node).getSymbol().getType() == JavaParser.LBRACK) {
        ArrayType arrayType = createData(ArrayType.class, typeNode, node);
        arrayType.setElementType(type);
        type = arrayType;
      }
    }

    return type;
  }
}
