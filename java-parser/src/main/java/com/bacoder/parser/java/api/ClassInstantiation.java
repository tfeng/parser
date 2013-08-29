package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

public class ClassInstantiation extends Invocation implements ExpressionInScope {

  private List<ClassMemberDeclaration> classMemberDeclarations = Collections.emptyList();

  private InstantiableType type;

  public List<ClassMemberDeclaration> getClassMemberDeclarations() {
    return classMemberDeclarations;
  }

  public InstantiableType getType() {
    return type;
  }

  public void setClassMemberDeclarations(List<ClassMemberDeclaration> classMemberDeclarations) {
    this.classMemberDeclarations = classMemberDeclarations;
  }

  public void setType(InstantiableType type) {
    this.type = type;
  }
}
