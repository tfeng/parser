package com.bacoder.parser.core;

public class KeyValuePair<K extends Node, V extends Node> extends Node {

  private K key;

  private V value;

  public K getKey() {
    return key;
  }

  public V getValue() {
    return value;
  }

  public void setKey(K key) {
    this.key = key;
  }

  public void setValue(V value) {
    this.value = value;
  }
}
