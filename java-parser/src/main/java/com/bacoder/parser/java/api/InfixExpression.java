package com.bacoder.parser.java.api;

import com.bacoder.parser.core.Node;

public class InfixExpression extends Node implements Expression {

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
}
