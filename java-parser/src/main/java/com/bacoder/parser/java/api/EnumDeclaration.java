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

public class EnumDeclaration extends TypeDeclaration
    implements AnnotationMemberDeclaration, InterfaceMemberDeclaration, ClassMemberDeclaration {

  private List<EnumConstant> constants = Collections.emptyList();

  private List<Type> implementsTypes = Collections.emptyList();

  private List<ClassMemberDeclaration> memberDeclarations = Collections.emptyList();

  private Identifier name;

  public List<EnumConstant> getConstants() {
    return constants;
  }

  public List<Type> getImplementsTypes() {
    return implementsTypes;
  }

  public List<ClassMemberDeclaration> getMemberDeclarations() {
    return memberDeclarations;
  }

  public Identifier getName() {
    return name;
  }

  public void setConstants(List<EnumConstant> constants) {
    this.constants = constants;
  }

  public void setImplementsTypes(List<Type> implementsTypes) {
    this.implementsTypes = implementsTypes;
  }

  public void setMemberDeclarations(List<ClassMemberDeclaration> memberDeclarations) {
    this.memberDeclarations = memberDeclarations;
  }

  public void setName(Identifier name) {
    this.name = name;
  }
}
