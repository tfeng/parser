package com.bacoder.parser.javaproperties.api;

import java.util.List;

import com.bacoder.parser.core.Node;

public class Properties extends Node {

  private List<Comment> comments;

  private List<KeyValue> keyValues;

  public List<Comment> getComments() {
    return comments;
  }

  public List<KeyValue> getKeyValues() {
    return keyValues;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void setKeyValues(List<KeyValue> keyValues) {
    this.keyValues = keyValues;
  }
}
