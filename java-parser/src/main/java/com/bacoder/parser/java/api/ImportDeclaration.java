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

import com.bacoder.parser.core.Node;

public class ImportDeclaration extends Node {

  private boolean isAsterisk;

  private boolean isStatic;

  private QualifiedName qualifiedName;

  public QualifiedName getQualifiedName() {
    return qualifiedName;
  }

  public boolean isAsterisk() {
    return isAsterisk;
  }

  public boolean isStatic() {
    return isStatic;
  }

  public void setAsterisk(boolean isAsterisk) {
    this.isAsterisk = isAsterisk;
  }

  public void setQualifiedName(QualifiedName qualifiedName) {
    this.qualifiedName = qualifiedName;
  }

  public void setStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }
}
