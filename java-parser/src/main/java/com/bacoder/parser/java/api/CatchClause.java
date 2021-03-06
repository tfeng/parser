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

public class CatchClause extends NodeWithModifiers {

  private Block body;

  private List<QualifiedName> exceptions = Collections.emptyList();

  private Identifier variable;

  public Block getBody() {
    return body;
  }

  public List<QualifiedName> getExceptions() {
    return exceptions;
  }

  public Identifier getVariable() {
    return variable;
  }

  public void setBody(Block body) {
    this.body = body;
  }

  public void setExceptions(List<QualifiedName> exceptions) {
    this.exceptions = exceptions;
  }

  public void setVariable(Identifier variable) {
    this.variable = variable;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    super.visitChildren(visitors);
    for (QualifiedName exception : exceptions) {
      exception.visit(visitors);
    }
    if (variable != null) {
      variable.visit(visitors);
    }
    if (body != null) {
      body.visit(visitors);
    }
  }
}
