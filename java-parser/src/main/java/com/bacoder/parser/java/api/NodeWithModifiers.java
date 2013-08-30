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

public abstract class NodeWithModifiers extends JavaNode {

  private List<Annotation> annotations = Collections.emptyList();

  private boolean isAbstract;

  private boolean isFinal;

  private boolean isNative;

  private boolean isPrivate;

  private boolean isProtected;

  private boolean isPublic;

  private boolean isStatic;

  private boolean isStrictfp;

  private boolean isSynchronized;

  private boolean isTransient;

  private boolean isVolatile;

  public List<Annotation> getAnnotations() {
    return annotations;
  }

  public boolean isAbstract() {
    return isAbstract;
  }

  public boolean isFinal() {
    return isFinal;
  }

  public boolean isNative() {
    return isNative;
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

  public boolean isSynchronized() {
    return isSynchronized;
  }

  public boolean isTransient() {
    return isTransient;
  }

  public boolean isVolatile() {
    return isVolatile;
  }

  public void setAbstract(boolean isAbstract) {
    this.isAbstract = isAbstract;
  }

  public void setAnnotations(List<Annotation> annotations) {
    this.annotations = annotations;
  }

  public void setFinal(boolean isFinal) {
    this.isFinal = isFinal;
  }

  public void setNative(boolean isNative) {
    this.isNative = isNative;
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

  public void setSynchronized(boolean isSynchronized) {
    this.isSynchronized = isSynchronized;
  }

  public void setTransient(boolean isTransient) {
    this.isTransient = isTransient;
  }

  public void setVolatile(boolean isVolatile) {
    this.isVolatile = isVolatile;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    for (Annotation annotation : annotations) {
      annotation.visit(visitors);
    }
  }
}
