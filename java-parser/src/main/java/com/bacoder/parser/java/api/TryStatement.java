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

public class TryStatement extends Statement {

  private Block body;

  private List<CatchClause> catchClauses = Collections.emptyList();

  private Block finallyBlock;

  private List<Resource> resources = Collections.emptyList();

  public Block getBody() {
    return body;
  }

  public List<CatchClause> getCatchClauses() {
    return catchClauses;
  }

  public Block getFinallyBlock() {
    return finallyBlock;
  }

  public List<Resource> getResources() {
    return resources;
  }

  public void setBody(Block body) {
    this.body = body;
  }

  public void setCatchClauses(List<CatchClause> catchClauses) {
    this.catchClauses = catchClauses;
  }

  public void setFinallyBlock(Block finallyBlock) {
    this.finallyBlock = finallyBlock;
  }

  public void setResources(List<Resource> resources) {
    this.resources = resources;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    super.visitChildren(visitors);
    for (Resource resource : resources) {
      resource.visit(visitors);
    }
    if (body != null) {
      body.visit(visitors);
    }
    for (CatchClause catchClause : catchClauses) {
      catchClause.visit(visitors);
    }
    if (finallyBlock != null) {
      finallyBlock.visit(visitors);
    }
  }
}
