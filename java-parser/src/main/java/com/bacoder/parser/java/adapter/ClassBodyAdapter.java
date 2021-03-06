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
import com.bacoder.parser.java.JavaParser.ClassBodyContext;
import com.bacoder.parser.java.JavaParser.ClassBodyDeclarationContext;
import com.bacoder.parser.java.api.ClassMemberDeclaration;
import com.google.common.base.Function;

public class ClassBodyAdapter extends JavaAdapter<ClassBodyContext, List<ClassMemberDeclaration>> {

  public ClassBodyAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<ClassMemberDeclaration> adapt(ClassBodyContext context) {
    return transform(context, ClassBodyDeclarationContext.class,
        new Function<ClassBodyDeclarationContext, ClassMemberDeclaration>() {
          @Override
          public ClassMemberDeclaration apply(ClassBodyDeclarationContext context) {
            return getAdapter(ClassBodyDeclarationAdapter.class).adapt(context);
          }
        });
  }
}
