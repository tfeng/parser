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
