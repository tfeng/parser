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

public class ForEachControl extends NodeWithModifiers implements ForControl {

  private Expression iterable;

  private Type type;

  private Identifier variable;

  public Expression getIterable() {
    return iterable;
  }

  public Type getType() {
    return type;
  }

  public Identifier getVariable() {
    return variable;
  }

  public void setIterable(Expression iterable) {
    this.iterable = iterable;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setVariable(Identifier variable) {
    this.variable = variable;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    super.visitChildren(visitors);
    if (type != null) {
      type.visit(visitors);
    }
    if (variable != null) {
      variable.visit(visitors);
    }
    if (iterable != null) {
      iterable.visit(visitors);
    }
  }
}
