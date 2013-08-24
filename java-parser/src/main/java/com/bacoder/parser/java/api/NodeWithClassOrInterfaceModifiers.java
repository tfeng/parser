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


public class NodeWithClassOrInterfaceModifiers extends NodeWithAnnotations {

  private boolean isAbstract;

  private boolean isFinal;

  private boolean isPrivate;

  private boolean isProtected;

  private boolean isPublic;

  private boolean isStatic;

  private boolean isStrictfp;

  public boolean isAbstract() {
    return isAbstract;
  }

  public boolean isFinal() {
    return isFinal;
  }

  public boolean isPrivate() {
    return isPrivate;
  }

  public boolean isProtected() {
    return isProtected;
  }

  public boolean isPublic() {
    return isPublic;
  }

  public boolean isStatic() {
    return isStatic;
  }

  public boolean isStrictfp() {
    return isStrictfp;
  }

  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }

  public void setFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }

  public void setPrivate(boolean isPrivate) {
    this.isPrivate = isPrivate;
  }

  public void setProtected(boolean isProtected) {
    this.isProtected = isProtected;
  }

  public void setPublic(boolean isPublic) {
    this.isPublic = isPublic;
  }

  public void setStatic(boolean isStatic) {
    this.isStatic = isStatic;
  }

  public void setStrictfp(boolean isStrictfp) {
    this.isStrictfp = isStrictfp;
  }
}
