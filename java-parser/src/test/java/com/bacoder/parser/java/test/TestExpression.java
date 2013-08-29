package com.bacoder.parser.java.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.bacoder.parser.java.adapter.ExpressionAdapter;
import com.bacoder.parser.java.api.Identifier;
import com.bacoder.parser.java.api.InfixExpression;
import com.bacoder.parser.java.api.Literal;
import com.bacoder.parser.java.api.TernaryExpression;
import com.srctran.backend.parser.java.JavaParser;

@Test
public class TestExpression extends JavaBaseTest {

  private ExpressionAdapter adapter = getAdapter(ExpressionAdapter.class);

  public void testInfixExpression1() {
    for (InfixExpression.Operator operator : InfixExpression.Operator.values()) {
      InfixExpression exp = parse("x" + operator + "y");
      Assert.assertEquals(((Identifier) exp.getLeftHandSide()).getText(), "x");
      Assert.assertEquals(exp.getOperator(), operator);
      Assert.assertEquals(((Identifier) exp.getRightHandSide()).getText(), "y");
    }
  }

  public void testInfixExpression2() {
    InfixExpression exp = parse("1 + (2 - 3)");

    Assert.assertEquals(((Literal) exp.getLeftHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) exp.getLeftHandSide()).getValue(), "1");
    Assert.assertEquals(exp.getOperator(), InfixExpression.Operator.ADD);

    InfixExpression rhs = (InfixExpression) exp.getRightHandSide();
    Assert.assertEquals(((Literal) rhs.getLeftHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) rhs.getLeftHandSide()).getValue(), "2");
    Assert.assertEquals(rhs.getOperator(), InfixExpression.Operator.SUBTRACT);
    Assert.assertEquals(((Literal) rhs.getRightHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) rhs.getRightHandSide()).getValue(), "3");
  }

  public void testTernaryExpression() {
    TernaryExpression exp = (TernaryExpression) parse("1 > 0 ? true : false");

    InfixExpression cond = (InfixExpression) exp.getCondition();
    Assert.assertEquals(((Literal) cond.getLeftHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) cond.getLeftHandSide()).getValue(), "1");
    Assert.assertEquals(cond.getOperator(), InfixExpression.Operator.GREATER);
    Assert.assertEquals(((Literal) cond.getRightHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) cond.getRightHandSide()).getValue(), "0");

    Assert.assertEquals(((Literal) exp.getTrueExpression()).getType(), Literal.Type.BOOLEAN);
    Assert.assertEquals(((Literal) exp.getTrueExpression()).getValue(), "true");

    Assert.assertEquals(((Literal) exp.getFalseExpression()).getType(), Literal.Type.BOOLEAN);
    Assert.assertEquals(((Literal) exp.getFalseExpression()).getValue(), "false");
  }

  @SuppressWarnings("unchecked")
  private <T> T parse(String input) {
    JavaParser parser = getParser(input);
    return (T) adapter.adapt(parser.expression(0));
  }
}
