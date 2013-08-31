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
import com.bacoder.parser.java.JavaParser.LocalVariableDeclarationContext;
import com.bacoder.parser.java.JavaParser.TypeContext;
import com.bacoder.parser.java.JavaParser.VariableDeclaratorsContext;
import com.bacoder.parser.java.api.LocalVariableDeclaration;

public class LocalVariableDeclarationAdapter
    extends JavaAdapter<LocalVariableDeclarationContext, LocalVariableDeclaration> {

  public LocalVariableDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public LocalVariableDeclaration adapt(LocalVariableDeclarationContext context) {
    LocalVariableDeclaration localVariableDeclaration = createNode(context);

    setModifiers(context, localVariableDeclaration);

    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      localVariableDeclaration.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
    }

    VariableDeclaratorsContext variableDeclaratorsContext =
        getChild(context, VariableDeclaratorsContext.class);
    if (variableDeclaratorsContext != null) {
      localVariableDeclaration.setVariableDeclarations(
          getAdapter(VariableDeclaratorsAdapter.class).adapt(variableDeclaratorsContext,
              localVariableDeclaration.getType(), typeContext));
    }

    return localVariableDeclaration;
  }
}
