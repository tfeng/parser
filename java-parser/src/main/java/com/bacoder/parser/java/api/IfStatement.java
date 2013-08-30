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

public class IfStatement extends Statement {

  private Expression condition;

  private Statement elseStatement;

  private Statement thenStatement;

  public Expression getCondition() {
    return condition;
  }

  public Statement getElseStatement() {
    return elseStatement;
  }

  public Statement getThenStatement() {
    return thenStatement;
  }

  public void setCondition(Expression condition) {
    this.condition = condition;
  }

  public void setElseStatement(Statement elseStatement) {
    this.elseStatement = elseStatement;
  }

  public void setThenStatement(Statement thenStatement) {
    this.thenStatement = thenStatement;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    super.visitChildren(visitors);
    if (condition != null) {
      condition.visit(visitors);
    }
    if (thenStatement != null) {
      thenStatement.visit(visitors);
    }
    if (elseStatement != null) {
      elseStatement.visit(visitors);
    }
  }
}
