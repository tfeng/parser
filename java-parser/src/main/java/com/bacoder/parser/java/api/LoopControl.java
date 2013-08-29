package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Node;

public class LoopControl extends Node implements ForControl {

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
}
