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
    Assert.assertEquals(((Literal) exp.getLeftHandSide()).getText(), "1");
    Assert.assertEquals(exp.getOperator(), InfixExpression.Operator.ADD);

    InfixExpression rhs = (InfixExpression) exp.getRightHandSide();
    Assert.assertEquals(((Literal) rhs.getLeftHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) rhs.getLeftHandSide()).getText(), "2");
    Assert.assertEquals(rhs.getOperator(), InfixExpression.Operator.SUBTRACT);
    Assert.assertEquals(((Literal) rhs.getRightHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) rhs.getRightHandSide()).getText(), "3");
  }

  public void testTernaryExpression() {
    TernaryExpression exp = (TernaryExpression) parse("1 > 0 ? true : false");

    InfixExpression cond = (InfixExpression) exp.getCondition();
    Assert.assertEquals(((Literal) cond.getLeftHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) cond.getLeftHandSide()).getText(), "1");
    Assert.assertEquals(cond.getOperator(), InfixExpression.Operator.GREATER);
    Assert.assertEquals(((Literal) cond.getRightHandSide()).getType(), Literal.Type.INTEGER);
    Assert.assertEquals(((Literal) cond.getRightHandSide()).getText(), "0");

    Assert.assertEquals(((Literal) exp.getThenExpression()).getType(), Literal.Type.BOOLEAN);
    Assert.assertEquals(((Literal) exp.getThenExpression()).getText(), "true");

    Assert.assertEquals(((Literal) exp.getElseExpression()).getType(), Literal.Type.BOOLEAN);
    Assert.assertEquals(((Literal) exp.getElseExpression()).getText(), "false");

    // exp.visit(new DumpVisitors(System.out));
  }

  @SuppressWarnings("unchecked")
  private <T> T parse(String input) {
    JavaParser parser = getParser(input);
    return (T) adapter.adapt(parser.expression(0));
  }
}
