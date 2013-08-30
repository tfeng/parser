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

public class ArrayCreation extends JavaNode implements Expression {

  private List<ArrayCreationDimension> dimensions = Collections.emptyList();

  private InstantiableType elementType;

  private ArrayInitializer initializer;

  public List<ArrayCreationDimension> getDimensions() {
    return dimensions;
  }

  public InstantiableType getElementType() {
    return elementType;
  }

  public ArrayInitializer getInitializer() {
    return initializer;
  }

  public void setDimensions(List<ArrayCreationDimension> dimensions) {
    this.dimensions = dimensions;
  }

  public void setElementType(InstantiableType elementType) {
    this.elementType = elementType;
  }

  public void setInitializer(ArrayInitializer initializer) {
    this.initializer = initializer;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    if (elementType != null) {
      elementType.visit(visitors);
    }
    for (ArrayCreationDimension dimension : dimensions) {
      dimension.visit(visitors);
    }
    if (initializer != null) {
      initializer.visit(visitors);
    }
  }
}
