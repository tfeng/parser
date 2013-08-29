package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

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
}
