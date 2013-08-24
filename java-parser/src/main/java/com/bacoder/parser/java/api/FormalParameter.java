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


public class FormalParameter extends NodeWithVariableModifiers {

  private Identifier identifier;

  private boolean isVariable;

  private Type type;

  public Identifier getIdentifier() {
    return identifier;
  }

  public Type getType() {
    return type;
  }

  public boolean isVariable() {
    return isVariable;
  }

  public void setIdentifier(Identifier identifier) {
    this.identifier = identifier;
  }

  public void setType(Type type) {
    this.type = type;
  }

  public void setVariable(boolean isVariable) {
    this.isVariable = isVariable;
  }
}
