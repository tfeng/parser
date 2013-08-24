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

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.AnyTypeArgument;
import com.bacoder.parser.java.api.SingleTypeArgument;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.TypeArgument;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.TypeArgumentContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;

public class TypeArgumentAdapter extends JavaAdapter<TypeArgumentContext, TypeArgument> {

  public TypeArgumentAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public TypeArgument adapt(TypeArgumentContext context) {
    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext == null) {
      AnyTypeArgument anyTypeArgument = createData(AnyTypeArgument.class, context);
      return anyTypeArgument;
    } else {
      Type type = getAdapter(TypeAdapter.class).adapt(typeContext);
      boolean hasExtends = hasTerminalNode(context, JavaParser.EXTENDS);
      boolean hasSuper = hasTerminalNode(context, JavaParser.SUPER);
      if (hasExtends || hasSuper) {
        AnyTypeArgument anyTypeArgument = createData(AnyTypeArgument.class, context);
        if (hasExtends) {
          anyTypeArgument.setExtendsType(type);
        } else {
          anyTypeArgument.setSuperType(type);
        }
        return anyTypeArgument;
      } else {
        SingleTypeArgument singleTypeArgument = createData(SingleTypeArgument.class, context);
        singleTypeArgument.setType(type);
        return singleTypeArgument;
      }
    }
  }
}
