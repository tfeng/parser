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

public class Annotation extends JavaNode implements AnnotationValue {

  private QualifiedName name;

  private AnnotationValue value;

  private List<NameValuePair> values = Collections.emptyList();

  public QualifiedName getName() {
    return name;
  }

  public AnnotationValue getValue() {
    return value;
  }

  public List<NameValuePair> getValues() {
    return values;
  }

  public void setName(QualifiedName name) {
    this.name = name;
  }

  public void setValue(AnnotationValue value) {
    this.value = value;
  }

  public void setValues(List<NameValuePair> values) {
    this.values = values;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    if (getName() != null) {
      getName().visit(visitors);
    }
    for (NameValuePair value : getValues()) {
      value.visit(visitors);
    }
    if (getValue() != null) {
      getValue().visit(visitors);
    }
  }
}
