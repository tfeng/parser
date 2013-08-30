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
package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Visitors;

public class TypeParameter extends JavaNode {

  private List<Type> extendsTypes = Collections.emptyList();

  private Identifier name;

  public List<Type> getExtendsTypes() {
    return extendsTypes;
  }

  public Identifier getName() {
    return name;
  }

  public void setExtendsTypes(List<Type> extendsTypes) {
    this.extendsTypes = extendsTypes;
  }

  public void setName(Identifier name) {
    this.name = name;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    if (name != null) {
     name.visit(visitors);
    }
    for (Type extendsType : extendsTypes) {
      extendsType.visit(visitors);
    }
  }
}
