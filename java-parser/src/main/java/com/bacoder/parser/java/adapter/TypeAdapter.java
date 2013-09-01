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
import com.bacoder.parser.java.JavaParser.ClassOrInterfaceTypeContext;
import com.bacoder.parser.java.JavaParser.PrimitiveTypeContext;
import com.bacoder.parser.java.JavaParser.TypeContext;
import com.bacoder.parser.java.api.ClassOrInterfaceType;
import com.bacoder.parser.java.api.PrimitiveType;
import com.bacoder.parser.java.api.Type;

public class TypeAdapter extends JavaAdapter<TypeContext, Type> {

  public TypeAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Type adapt(TypeContext context) {
    ClassOrInterfaceTypeContext classOrInterfaceTypeContext =
        getChild(context, ClassOrInterfaceTypeContext.class);
    if (classOrInterfaceTypeContext != null) {
      ClassOrInterfaceType type =
          getAdapter(ClassOrInterfaceTypeAdapter.class).adapt(classOrInterfaceTypeContext);
      type.setDimensions(getAdapter(ArrayDimensionsAdapter.class).adapt(context));
      return type;
    }

    PrimitiveTypeContext primitiveTypeContext = getChild(context, PrimitiveTypeContext.class);
    if (primitiveTypeContext != null) {
      PrimitiveType type = getAdapter(PrimitiveTypeAdapter.class).adapt(primitiveTypeContext);
      type.setDimensions(getAdapter(ArrayDimensionsAdapter.class).adapt(context));
      return type;
    }

    return null;
  }
}
