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
package com.bacoder.parser.core;

public abstract class Node implements Segment {

  private int endColumn;

  private int endLine;

  private int startColumn;

  private int startLine;

  @Override
  public int getEndColumn() {
    return endColumn;
  }

  @Override
  public int getEndLine() {
    return endLine;
  }

  @Override
  public int getStartColumn() {
    return startColumn;
  }

  @Override
  public int getStartLine() {
    return startLine;
  }

  public void setEndColumn(int endColumn) {
    this.endColumn = endColumn;
  }

  public void setEndLine(int endLine) {
    this.endLine = endLine;
  }

  public void setStartColumn(int startColumn) {
    this.startColumn = startColumn;
  }

  public void setStartLine(int startLine) {
    this.startLine = startLine;
  }

  @Override
  public void visit(Visitors visitors) {
    visitors.visitBefore(this);
    visitChildren(visitors);
    visitors.visitAfter(this);
  }

  protected abstract void visitChildren(Visitors visitors);
}
