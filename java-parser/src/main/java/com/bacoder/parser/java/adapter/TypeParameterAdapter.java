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

import com.google.common.base.Function;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.TypeParameter;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.TypeBoundContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;
import com.srctran.backend.parser.java.JavaParser.TypeParameterContext;

public class TypeParameterAdapter extends JavaAdapter<TypeParameterContext, TypeParameter> {

  public TypeParameterAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public TypeParameter adapt(TypeParameterContext context) {
    TypeParameter typeParameter = createData(context);

    TerminalNode identifierNode = getChild(context, TerminalNode.class);
    if (identifierNode != null && identifierNode.getSymbol().getType() == JavaParser.Identifier) {
      typeParameter.setIdentifier(
          getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    TypeBoundContext typeBoundContext = getChild(context, TypeBoundContext.class);
    if (typeBoundContext != null) {
      List<Type> typeBounds =
          transform(typeBoundContext, TypeContext.class, new Function<TypeContext, Type>() {
            @Override
            public Type apply(TypeContext context) {
              return getAdapter(TypeAdapter.class).adapt(context);
            }
          });
      typeParameter.setTypeBounds(typeBounds);
    }

    return typeParameter;
  }
}
