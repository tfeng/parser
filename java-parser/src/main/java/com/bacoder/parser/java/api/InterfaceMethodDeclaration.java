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

public class InterfaceMethodDeclaration extends NodeWithModifiers
    implements InterfaceMemberDeclaration {

  private List<ArrayDimension> dimensions = Collections.emptyList();

  private List<FormalParameter> formalParameters = Collections.emptyList();

  private Identifier name;

  private TypeOrVoid returnType;

  private List<QualifiedName> throwsExceptions = Collections.emptyList();

  private List<TypeParameter> typeParameters = Collections.emptyList();

  public List<ArrayDimension> getDimensions() {
    return dimensions;
  }

  public List<FormalParameter> getFormalParameters() {
    return formalParameters;
  }

  public Identifier getName() {
    return name;
  }

  public TypeOrVoid getReturnType() {
    return returnType;
  }

  public List<QualifiedName> getThrowsExceptions() {
    return throwsExceptions;
  }

  public List<TypeParameter> getTypeParameters() {
    return typeParameters;
  }

  public boolean isVoid() {
    return returnType instanceof VoidType;
  }

  public void setDimensions(List<ArrayDimension> dimensions) {
    this.dimensions = dimensions;
  }

  public void setFormalParameters(List<FormalParameter> formalParameters) {
    this.formalParameters = formalParameters;
  }

  public void setName(Identifier name) {
    this.name = name;
  }

  public void setReturnType(TypeOrVoid returnType) {
    this.returnType = returnType;
  }

  public void setThrowsExceptions(List<QualifiedName> throwsExceptions) {
    this.throwsExceptions = throwsExceptions;
  }

  public void setTypeParameters(List<TypeParameter> typeParameters) {
    this.typeParameters = typeParameters;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    super.visitChildren(visitors);
    for (TypeParameter typeParameter : typeParameters) {
      typeParameter.visit(visitors);
    }
    if (returnType != null) {
      returnType.visit(visitors);
    }
    if (name != null) {
      name.visit(visitors);
    }
    for (FormalParameter formalParameter : formalParameters) {
      formalParameter.visit(visitors);
    }
    for (ArrayDimension dimension : dimensions) {
      dimension.visit(visitors);
    }
    for (QualifiedName throwsException : throwsExceptions) {
      throwsException.visit(visitors);
    }
  }
}
