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

public abstract class Node {

  private int endIndex;

  private int endLine;

  private int startIndex;

  private int startLine;

  public int getEndIndex() {
    return endIndex;
  }

  public int getEndLine() {
    return endLine;
  }

  public int getStartIndex() {
    return startIndex;
  }

  public int getStartLine() {
    return startLine;
  }

  public void setEndIndex(int endIndex) {
    this.endIndex = endIndex;
  }

  public void setEndLine(int endLine) {
    this.endLine = endLine;
  }

  public void setStartIndex(int startIndex) {
    this.startIndex = startIndex;
  }

  public void setStartLine(int startLine) {
    this.startLine = startLine;
  }
}
