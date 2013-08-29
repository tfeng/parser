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

public class InterfaceMethodDeclaration extends NodeWithModifiers
    implements InterfaceMemberDeclaration {

  private List<FormalParameter> formalParameters = Collections.emptyList();

  private Identifier name;

  private ReturnType returnType;

  private List<QualifiedName> throwsExceptions = Collections.emptyList();

  private List<TypeParameter> typeParameters = Collections.emptyList();

  public List<FormalParameter> getFormalParameters() {
    return formalParameters;
  }

  public Identifier getName() {
    return name;
  }

  public ReturnType getReturnType() {
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

  public void setFormalParameters(List<FormalParameter> formalParameters) {
    this.formalParameters = formalParameters;
  }

  public void setName(Identifier name) {
    this.name = name;
  }

  public void setReturnType(ReturnType returnType) {
    this.returnType = returnType;
  }

  public void setThrowsExceptions(List<QualifiedName> throwsExceptions) {
    this.throwsExceptions = throwsExceptions;
  }

  public void setTypeParameters(List<TypeParameter> typeParameters) {
    this.typeParameters = typeParameters;
  }
}
