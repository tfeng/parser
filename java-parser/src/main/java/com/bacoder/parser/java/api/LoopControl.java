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

public class LoopControl extends JavaNode implements ForControl {

  private Expression condition;

  private List<Expression> initExpressions = Collections.emptyList();

  private List<Expression> updateExpressions = Collections.emptyList();

  private LocalVariableDeclaration variableDeclaration;

  public Expression getCondition() {
    return condition;
  }

  public List<Expression> getInitExpressions() {
    return initExpressions;
  }

  public List<Expression> getUpdateExpressions() {
    return updateExpressions;
  }

  public LocalVariableDeclaration getVariableDeclaration() {
    return variableDeclaration;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public void setInitExpressions(List<Expression> initExpressions) {
    this.initExpressions = initExpressions;
  }

  public void setUpdateExpressions(List<Expression> updateExpressions) {
    this.updateExpressions = updateExpressions;
  }

  public void setVariableDeclaration(LocalVariableDeclaration variableDeclaration) {
    this.variableDeclaration = variableDeclaration;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    if (variableDeclaration != null) {
      variableDeclaration.visit(visitors);
    }
    for (Expression initExpression : initExpressions) {
      initExpression.visit(visitors);
    }
    if (condition != null) {
      condition.visit(visitors);
    }
    for (Expression updateExpression : updateExpressions) {
      updateExpression.visit(visitors);
    }
  }
}
