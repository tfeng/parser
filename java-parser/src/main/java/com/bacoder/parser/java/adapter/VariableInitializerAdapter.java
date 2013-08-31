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
import com.bacoder.parser.java.JavaParser.ArrayInitializerContext;
import com.bacoder.parser.java.JavaParser.ExpressionContext;
import com.bacoder.parser.java.JavaParser.VariableInitializerContext;
import com.bacoder.parser.java.api.VariableInitializer;

public class VariableInitializerAdapter
    extends JavaAdapter<VariableInitializerContext, VariableInitializer> {

  public VariableInitializerAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public VariableInitializer adapt(VariableInitializerContext context) {
    ArrayInitializerContext arrayInitializerContext =
        getChild(context, ArrayInitializerContext.class);
    if (arrayInitializerContext != null) {
      return getAdapter(ArrayInitializerAdapter.class).adapt(arrayInitializerContext);
    }

    ExpressionContext expressionContext = getChild(context, ExpressionContext.class);
    if (expressionContext != null) {
      return getAdapter(ExpressionAdapter.class).adapt(expressionContext);
    }

    return null;
  }
}
