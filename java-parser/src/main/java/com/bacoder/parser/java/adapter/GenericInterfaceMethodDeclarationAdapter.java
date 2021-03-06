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
import com.bacoder.parser.java.JavaParser.GenericInterfaceMethodDeclarationContext;
import com.bacoder.parser.java.JavaParser.InterfaceMethodDeclarationContext;
import com.bacoder.parser.java.JavaParser.TypeParametersContext;
import com.bacoder.parser.java.api.InterfaceMethodDeclaration;

public class GenericInterfaceMethodDeclarationAdapter
    extends JavaAdapter<GenericInterfaceMethodDeclarationContext, InterfaceMethodDeclaration> {

  public GenericInterfaceMethodDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public InterfaceMethodDeclaration adapt(GenericInterfaceMethodDeclarationContext context) {
    InterfaceMethodDeclarationContext interfaceDeclarationContext =
        getChild(context, InterfaceMethodDeclarationContext.class);
    if (interfaceDeclarationContext != null) {
      InterfaceMethodDeclaration interfaceMethodDeclaration =
          getAdapter(InterfaceMethodDeclarationAdapter.class).adapt(interfaceDeclarationContext);
      TypeParametersContext typeParametersContext = getChild(context, TypeParametersContext.class);
      if (typeParametersContext != null) {
        interfaceMethodDeclaration.setTypeParameters(
            getAdapter(TypeParametersAdapter.class).adapt(typeParametersContext));
      }
      return interfaceMethodDeclaration;
    }

    return null;
  }
}
