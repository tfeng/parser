package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Node;

public class ArrayCreation extends Node implements Expression {

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
}
