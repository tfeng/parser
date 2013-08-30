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

public class InfixExpression extends JavaNode implements Expression {

  public static enum Operator {
    ADD("+"),
    ADD_ASSIGN("+="),
    AND("&&"),
    ASSIGN("="),
    BIT_AND("&"),
    BIT_AND_ASSIGN("&="),
    BIT_OR("|"),
    BIT_OR_ASSIGN("|="),
    BIT_XOR("^"),
    BIT_XOR_ASSIGN("^="),
    DIVIDE("/"),
    DIVIDE_ASSIGN("/="),
    EQUAL("=="),
    GREATER(">"),
    GREATER_EQUAL(">="),
    LEFT_SHIFT("<<"),
    LEFT_SHIFT_ASSIGN("<<="),
    LESS("<"),
    LESS_EQUAL("<="),
    MOD("%"),
    MOD_ASSIGN("%="),
    MULTIPLY("*"),
    MULTIPLY_ASSIGN("*="),
    NOT_EQUAL("!="),
    OR("||"),
    SIGNED_RIGHT_SHIFT(">>"),
    SIGNED_RIGHT_SHIFT_ASSIGN(">>="),
    SUBTRACT("-"),
    SUBTRACT_ASSIGN("-="),
    UNSIGNED_RIGHT_SHIFT(">>>"),
    UNSIGNED_RIGHT_SHIFT_ASSIGN(">>>=");

    private String operator;

    private Operator(String operator) {
      this.operator = operator;
    }

    @Override
    public String toString() {
      return operator;
    }
  }

  private Expression leftHandSide;

  private Operator operator;

  private Expression rightHandSide;

  public Expression getLeftHandSide() {
    return leftHandSide;
  }

  public Operator getOperator() {
    return operator;
  }

  public Expression getRightHandSide() {
    return rightHandSide;
  }

  public void setLeftHandSide(Expression leftHandSide) {
    this.leftHandSide = leftHandSide;
  }

  public void setOperator(Operator operator) {
    this.operator = operator;
  }

  public void setRightHandSide(Expression rightHandSide) {
    this.rightHandSide = rightHandSide;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    if (leftHandSide != null) {
      leftHandSide.visit(visitors);
    }
    if (rightHandSide != null) {
      rightHandSide.visit(visitors);
    }
  }
}
