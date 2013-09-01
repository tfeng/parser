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

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser.NonWildcardTypeArgumentsContext;
import com.bacoder.parser.java.JavaParser.TypeListContext;
import com.bacoder.parser.java.api.Type;

public class NonWildcardTypeArgumentsAdapter
    extends JavaAdapter<NonWildcardTypeArgumentsContext, List<Type>> {

  public NonWildcardTypeArgumentsAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<Type> adapt(NonWildcardTypeArgumentsContext context) {
    TypeListContext typeListContext = getChild(context, TypeListContext.class);
    if (typeListContext != null) {
      return getAdapter(TypeListAdapter.class).adapt(typeListContext);
    }

    return Collections.emptyList();
  }
}
