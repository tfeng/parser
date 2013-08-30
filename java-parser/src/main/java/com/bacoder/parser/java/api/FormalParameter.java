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

import com.bacoder.parser.core.Visitors;

public class FormalParameter extends NodeWithModifiers {

  private boolean isVariable;

  private Identifier name;

  private Type type;

  public Identifier getName() {
    return name;
  }

  public Type getType() {
    return type;
  }

  public boolean isVariable() {
    return isVariable;
  }

  public void setName(Identifier name) {
    this.name = name;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setVariable(boolean isVariable) {
    this.isVariable = isVariable;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    super.visitChildren(visitors);
    if (type != null) {
      type.visit(visitors);
    }
    if (name != null) {
      name.visit(visitors);
    }
  }
}
