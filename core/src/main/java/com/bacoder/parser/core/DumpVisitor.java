package com.bacoder.parser.core;

import java.io.IOException;
import java.io.OutputStream;

import com.google.common.base.Strings;

public class DumpVisitor implements Visitor<Node> {

  private String indent = "  ";

  private int level;

  private OutputStream outputStream;

  public DumpVisitor(OutputStream outputStream) {
    this.outputStream = outputStream;
  }

  public DumpVisitor(OutputStream outputStream, String indent) {
    this.outputStream = outputStream;
    this.indent = indent;
  }

  @Override
  public void visitAfter(Node node) {
    level--;
    String s = String.format("%s</%s>\n", Strings.repeat(indent, level),
        node.getClass().getSimpleName());
    try {
      outputStream.write(s.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Unable to write \"" + s + "\"", e);
    }
  }

  @Override
  public void visitBefore(Node node) {
    String s = String.format("%s<%s> <!-- %d:%d-%d:%d -->\n", Strings.repeat(indent, level),
        node.getClass().getSimpleName(), node.getStartLine(), node.getStartColumn(),
        node.getEndLine(), node.getEndColumn());
    try {
      outputStream.write(s.getBytes());
    } catch (IOException e) {
      throw new RuntimeException("Unable to write \"" + s + "\"", e);
    }
    level++;
  }

}
