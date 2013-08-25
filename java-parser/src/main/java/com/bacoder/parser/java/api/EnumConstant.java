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

import com.bacoder.parser.core.Node;

public class EnumConstant extends Node {

  private List<Annotation> annotations = Collections.emptyList();

  private List<Expression> arguments = Collections.emptyList();

  private Identifier identifier;

  private List<ClassMemberDeclaration> memberDeclarations = Collections.emptyList();

  public List<Annotation> getAnnotations() {
    return annotations;
  }

  public List<Expression> getArguments() {
    return arguments;
  }

  public List<ClassMemberDeclaration> getMemberDeclarations() {
    return memberDeclarations;
  }

  public Identifier getIdentifier() {
    return identifier;
  }

  public void setAnnotations(List<Annotation> annotations) {
    this.annotations = annotations;
  }

  public void setArguments(List<Expression> arguments) {
    this.arguments = arguments;
  }

  public void setMemberDeclarations(List<ClassMemberDeclaration> memberDeclarations) {
    this.memberDeclarations = memberDeclarations;
  }

  public void setIdentifier(Identifier identifier) {
    this.identifier = identifier;
  }
}
