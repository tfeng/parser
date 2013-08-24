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

public class NodeWithModifiers extends NodeWithClassOrInterfaceModifiers {

  private boolean isNative;

  private boolean isSynchronized;

  private boolean isTransient;

  private boolean isVolatile;

  public boolean isNative() {
    return isNative;
  }

  public boolean isSynchronized() {
    return isSynchronized;
  }

  public boolean isTransient() {
    return isTransient;
  }

  public boolean isVolatile() {
    return isVolatile;
  }

  public void setNative(boolean isNative) {
    this.isNative = isNative;
  }

  public void setSynchronized(boolean isSynchronized) {
    this.isSynchronized = isSynchronized;
  }

  public void setTransient(boolean isTransient) {
    this.isTransient = isTransient;
  }

  public void setVolatile(boolean isVolatile) {
    this.isVolatile = isVolatile;
  }
}
