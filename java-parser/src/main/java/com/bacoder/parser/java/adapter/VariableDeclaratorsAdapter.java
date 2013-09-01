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

import org.antlr.v4.runtime.tree.ParseTree;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser.VariableDeclaratorContext;
import com.bacoder.parser.java.JavaParser.VariableDeclaratorsContext;
import com.bacoder.parser.java.api.Type;
import com.bacoder.parser.java.api.VariableDeclaration;
import com.google.common.base.Function;

public class VariableDeclaratorsAdapter
    extends JavaAdapter<VariableDeclaratorsContext, List<VariableDeclaration>> {

  public VariableDeclaratorsAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<VariableDeclaration> adapt(VariableDeclaratorsContext context) {
    throw new RuntimeException("Not supported");
  }

  public List<VariableDeclaration> adapt(VariableDeclaratorsContext context, final Type baseType,
      final ParseTree baseTypeContext) {
    return transform(context, VariableDeclaratorContext.class,
        new Function<VariableDeclaratorContext, VariableDeclaration>() {
          @Override
          public VariableDeclaration apply(VariableDeclaratorContext context) {
            return getAdapter(VariableDeclaratorAdapter.class).adapt(context);
          }
        });
  }
}
