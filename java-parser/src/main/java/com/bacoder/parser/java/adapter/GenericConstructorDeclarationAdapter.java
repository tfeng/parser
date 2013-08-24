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
import com.bacoder.parser.java.api.ConstructorDeclaration;
import com.srctran.backend.parser.java.JavaParser.ConstructorDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.GenericConstructorDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeParametersContext;

public class GenericConstructorDeclarationAdapter
    extends JavaAdapter<GenericConstructorDeclarationContext, ConstructorDeclaration> {

  public GenericConstructorDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public ConstructorDeclaration adapt(GenericConstructorDeclarationContext context) {
    ConstructorDeclarationContext constructorDeclarationContext =
        getChild(context, ConstructorDeclarationContext.class);
    if (constructorDeclarationContext == null) {
      ConstructorDeclaration constructorDeclaration =
          getAdapter(ConstructorDeclarationAdapter.class).adapt(constructorDeclarationContext);
      TypeParametersContext typeParametersContext = getChild(context, TypeParametersContext.class);
      if (typeParametersContext != null) {
        constructorDeclaration.setTypeParameters(
            getAdapter(TypeParametersAdapter.class).adapt(typeParametersContext));
      }
      return constructorDeclaration;
    }

    return null;
  }
}
