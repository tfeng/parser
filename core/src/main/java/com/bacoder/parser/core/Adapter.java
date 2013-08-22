package com.bacoder.parser.core;

import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.base.Function;

public abstract class Adapter<C extends ParseTree, D> {

  private Adapters adapters;

  private final Class<D> dataType;

  @SuppressWarnings("unchecked")
  public Adapter(Adapters adapters) {
    this.adapters = adapters;
    dataType = (Class<D>) ((ParameterizedType) getClass().getGenericSuperclass())
                              .getActualTypeArguments()[1];
  }

  public abstract D adapt(C context);

  public D createData(C context) {
    return createData(dataType, context, context);
  }

  public <T> T createData(Class<T> clazz, ParseTree context) {
    return createData(clazz, context, context);
  }

  public <T> T createData(Class<T> clazz, ParseTree startNode, ParseTree endNode) {
    try {
      T data = clazz.newInstance();
      setNodeAttributes(data, startNode, endNode);
      return data;
    } catch (Exception e) {
      throw new RuntimeException("Unable to create data object with " + clazz, e);
    }
  }

  @SuppressWarnings("unchecked")
  protected <T extends ParseTree> void forEachChild(ParserRuleContext context, Class<T> clazz,
      Function<T, Void> function) {
    for (ParseTree child : context.children) {
      if (clazz.isInstance(child)) {
        function.apply((T) child);
      }
    }
  }

  protected void forEachChild(ParserRuleContext context, Function<ParseTree, Void> function) {
    for (ParseTree child : context.children) {
      function.apply(child);
    }
  }

  public <T extends Adapter<?, ?>> T getAdapter(Class<T> clazz) {
    return adapters.getAdapter(clazz);
  }

  @SuppressWarnings("unchecked")
  protected <T extends ParseTree> T getChild(ParserRuleContext context, Class<T> clazz) {
    for (ParseTree child : context.children) {
      if (clazz.isInstance(child)) {
        return (T) child;
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  protected <T extends ParserRuleContext> List<T> getChildren(ParserRuleContext context,
      Class<T> clazz) {
    List<T> list = new ArrayList<T>();
    for (ParseTree child : context.children) {
      if (clazz.isInstance(child)) {
        list.add((T) child);
      }
    }
    return list;
  }

  protected TerminalNode getTerminalNode(ParserRuleContext context, final int symbolType) {
    return transformOne(context, TerminalNode.class, new Function<TerminalNode, TerminalNode>() {
          @Override
          public TerminalNode apply(TerminalNode node) {
            if (node.getSymbol().getType() == symbolType) {
              return node;
            } else {
              return null;
            }
          }
        });
  }

  protected boolean hasTerminalNode(ParserRuleContext context, int symbolType) {
    return getTerminalNode(context, symbolType) != null;
  }

  protected <T> void setNodeAttributes(T data, ParseTree node) {
    setNodeAttributes(data, node, node);
  }

  protected <T> void setNodeAttributes(T data, ParseTree startNode, ParseTree endNode) {
    if (data instanceof Node) {
      Node node = (Node) data;

      if (startNode instanceof ParserRuleContext) {
        ParserRuleContext context = (ParserRuleContext) startNode;
        node.setStartLine(context.getStart().getLine());
        node.setStartIndex(context.getStart().getStartIndex());
      } else if (startNode instanceof TerminalNode) {
        TerminalNode terminal = (TerminalNode) startNode;
        node.setStartLine(terminal.getSymbol().getLine());
        node.setStartIndex(terminal.getSymbol().getStartIndex());
      }

      if (endNode instanceof ParserRuleContext) {
        ParserRuleContext context = (ParserRuleContext) endNode;
        node.setEndLine(context.getStop().getLine());
        node.setEndIndex(context.getStop().getStopIndex());
      } else if (endNode instanceof TerminalNode) {
        TerminalNode terminal = (TerminalNode) endNode;
        node.setEndLine(terminal.getSymbol().getLine());
        node.setEndIndex(terminal.getSymbol().getStopIndex());
      }
    }
  }

  protected <T extends ParseTree, S> List<S> transform(ParserRuleContext context, Class<T> clazz,
      Function<T, S> function) {
    List<S> list = new ArrayList<S>();
    for (ParseTree child : context.children) {
      if (clazz.isInstance(child)) {
        @SuppressWarnings("unchecked")
        S result = function.apply((T) child);
        if (result != null) {
          list.add(result);
        }
      }
    }
    return list;
  }

  protected <T extends ParseTree, S> S transformOne(ParserRuleContext context, Class<T> clazz,
      Function<T, S> function) {
    for (ParseTree child : context.children) {
      if (clazz.isInstance(child)) {
        @SuppressWarnings("unchecked")
        S result = function.apply((T) child);
        if (result != null) {
          return result;
        }
      }
    }
    return null;
  }
}