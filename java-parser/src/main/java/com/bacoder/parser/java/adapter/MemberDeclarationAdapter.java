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
import com.bacoder.parser.java.api.MemberDeclaration;
import com.srctran.backend.parser.java.JavaParser.MemberDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.MethodDeclarationContext;

public class MemberDeclarationAdapter
    extends JavaAdapter<MemberDeclarationContext, MemberDeclaration> {

  public MemberDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public MemberDeclaration adapt(MemberDeclarationContext context) {
    MethodDeclarationContext methodDeclarationContext =
        getChild(context, MethodDeclarationContext.class);
    if (methodDeclarationContext != null) {
      return getAdapter(MethodDeclarationAdapter.class).adapt(methodDeclarationContext);
    }

    return null;
  }
}
