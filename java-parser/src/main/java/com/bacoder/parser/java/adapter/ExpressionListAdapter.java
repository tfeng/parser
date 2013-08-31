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

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser.ExpressionContext;
import com.bacoder.parser.java.JavaParser.ExpressionListContext;
import com.bacoder.parser.java.api.Expression;
import com.google.common.base.Function;

public class ExpressionListAdapter extends JavaAdapter<ExpressionListContext, List<Expression>> {

  public ExpressionListAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<Expression> adapt(ExpressionListContext context) {
    return transform(context, ExpressionContext.class,
        new Function<ExpressionContext, Expression>() {
          @Override
          public Expression apply(ExpressionContext context) {
            return getAdapter(ExpressionAdapter.class).adapt(context);
          }
        });
  }
}
