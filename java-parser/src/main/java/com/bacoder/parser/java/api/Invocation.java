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

public abstract class Invocation extends JavaNode implements Expression {

  private List<Expression> arguments = Collections.emptyList();

  private Expression scope;

  private List<? extends TypeArgument> typeArguments = Collections.emptyList();

  public List<Expression> getArguments() {
    return arguments;
  }

  public Expression getScope() {
    return scope;
  }

  public List<? extends TypeArgument> getTypeArguments() {
    return typeArguments;
  }

  public void setArguments(List<Expression> arguments) {
    this.arguments = arguments;
  }

  public void setScope(Expression scope) {
    this.scope = scope;
  }

  public void setTypeArguments(List<? extends TypeArgument> typeArguments) {
    this.typeArguments = typeArguments;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    for (TypeArgument typeArgument : typeArguments) {
      typeArgument.visit(visitors);
    }
    if (scope != null) {
      scope.visit(visitors);
    }
    visitName(visitors);
    for (Expression argument : arguments) {
      argument.visit(visitors);
    }
  }

  protected abstract void visitName(Visitors visitors);
}
