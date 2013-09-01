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
import com.bacoder.parser.java.JavaParser.ConstDeclarationContext;
import com.bacoder.parser.java.JavaParser.ConstantDeclaratorContext;
import com.bacoder.parser.java.JavaParser.TypeContext;
import com.bacoder.parser.java.api.ConstDeclaration;
import com.bacoder.parser.java.api.VariableDeclaration;
import com.google.common.base.Function;

public class ConstDeclarationAdapter
    extends JavaAdapter<ConstDeclarationContext, ConstDeclaration> {

  public ConstDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public ConstDeclaration adapt(ConstDeclarationContext context) {
    final ConstDeclaration constDeclaration = createNode(context);

    final TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      constDeclaration.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
    }

    constDeclaration.setVariableDeclarations(transform(context, ConstantDeclaratorContext.class,
        new Function<ConstantDeclaratorContext, VariableDeclaration>() {
          @Override
          public VariableDeclaration apply(ConstantDeclaratorContext context) {
            return getAdapter(ConstDeclaratorAdapter.class).adapt(context);
          }
        }));

    return constDeclaration;
  }
}
