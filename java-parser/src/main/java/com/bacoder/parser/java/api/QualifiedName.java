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

public class QualifiedName extends JavaNode {

  private static final String IDENTIFIER_SEPARATOR = ".";

  private List<Identifier> identifiers = Collections.emptyList();

  public String getFullName() {
    StringBuffer buffer = new StringBuffer();
    for (Identifier identifier : identifiers) {
      if (buffer.length() > 0) {
        buffer.append(IDENTIFIER_SEPARATOR);
      }
      buffer.append(identifier.getText());
    }
    return buffer.toString();
  }

  public List<Identifier> getIdentifiers() {
    return identifiers;
  }

  public void setIdentifiers(List<Identifier> identifiers) {
    this.identifiers = identifiers;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    for (Identifier identifier : identifiers) {
      identifier.visit(visitors);
    }
  }
}
