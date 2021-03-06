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

public class SwitchBlock extends JavaNode {

  private List<SwitchLabel> labels = Collections.emptyList();

  private List<BlockStatement> statements = Collections.emptyList();

  public List<SwitchLabel> getLabels() {
    return labels;
  }

  public List<BlockStatement> getStatements() {
    return statements;
  }

  public void setLabels(List<SwitchLabel> labels) {
    this.labels = labels;
  }

  public void setStatements(List<BlockStatement> statements) {
    this.statements = statements;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    for (SwitchLabel label : labels) {
      label.visit(visitors);
    }
    for (BlockStatement statement : statements) {
      statement.visit(visitors);
    }
  }
}
