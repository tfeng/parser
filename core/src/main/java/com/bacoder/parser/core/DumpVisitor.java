package com.bacoder.parser.core;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;

import org.apache.commons.beanutils.PropertyUtils;

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
    String tag = String.format("%s<%s sl=\"%d\" sc=\"%d\" el=\"%d\" ec=\"%d\">\n",
        Strings.repeat(indent, level), node.getClass().getSimpleName(), node.getStartLine(),
        node.getStartColumn(), node.getEndLine(), node.getEndColumn());
    try {
      outputStream.write(tag.getBytes());

      Field [] fields =  node.getClass().getDeclaredFields();
      for (Field field : fields) {
        if (field.getType().isPrimitive() || String.class.isAssignableFrom(field.getType())) {
          String propertyName = field.getName();
          Object value;
          try {
            value = PropertyUtils.getSimpleProperty(node, propertyName);
            String property = String.format("%s<%s>%s</%s>\n", Strings.repeat(indent, level + 1),
                propertyName, value == null ? "" : value.toString(), propertyName);
            try {
              outputStream.write(property.getBytes());
            } catch (IOException e) {
              throw new RuntimeException("Unable to write \'" + property + "\'", e);
            }
          } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            // Ignore the field.
          }
        }
      }
    } catch (IOException e) {
      throw new RuntimeException("Unable to write \'" + tag + "\'", e);
    }
    level++;
  }

  protected String getIndent() {
    return indent;
  }

  protected int getLevel() {
    return level;
  }

  protected OutputStream getOutputStream() {
    return outputStream;
  }
}
