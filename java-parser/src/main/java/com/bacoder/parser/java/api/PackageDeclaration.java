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

public class PackageDeclaration extends JavaNode {

  private List<Annotation> annotations = Collections.emptyList();

  private QualifiedName qualifiedName;

  public List<Annotation> getAnnotations() {
    return annotations;
  }

  public QualifiedName getQualifiedName() {
    return qualifiedName;
  }

  public void setAnnotations(List<Annotation> annotations) {
    this.annotations = annotations;
  }

  public void setQualifiedName(QualifiedName qualifiedName) {
    this.qualifiedName = qualifiedName;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    for (Annotation annotation : annotations) {
      annotation.visit(visitors);
    }
    if (qualifiedName != null) {
      qualifiedName.visit(visitors);
    }
  }
}
