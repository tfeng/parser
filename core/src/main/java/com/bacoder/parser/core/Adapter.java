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

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableMap;

public abstract class Adapter<C extends ParseTree, D> {

  private static final ImmutableMap<Class<?>, Class<?>> TYPE_MAPPING =
      ImmutableMap.of((Class<?>) List.class, (Class<?>) ArrayList.class,
                      (Class<?>) Map.class, (Class<?>) HashMap.class);

  private Adapters adapters;

  private final Class<D> dataType;

  @SuppressWarnings("unchecked")
  public Adapter(Adapters adapters) {
    this.adapters = adapters;
    Type type = ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
    Class<D> dataType;
    if (type instanceof ParameterizedType) {
      ParameterizedType parameterizedType = (ParameterizedType) type;
      dataType = (Class<D>) parameterizedType.getRawType();
    } else {
      dataType = (Class<D>) type;
    }
    if (TYPE_MAPPING.containsKey(dataType)) {
      dataType = (Class<D>) TYPE_MAPPING.get(dataType);
    }
    this.dataType = dataType;
  }

  public abstract D adapt(C context);

  public D createNode(C context) {
    return createNode(context, context, dataType);
  }

  public <T> T createNode(ParseTree context, Class<T> clazz) {
    return createNode(context, context, clazz);
  }

  public <T> T createNode(ParseTree startNode, ParseTree endNode, Class<T> clazz) {
    try {
      T data = clazz.newInstance();
      setNodeAttributes(data, startNode, endNode);
      return data;
    } catch (Exception e) {
      throw new RuntimeException("Unable to create data object with " + clazz, e);
    }
  }

  public <T extends Adapter<?, ?>> T getAdapter(Class<T> clazz) {
    return adapters.getAdapter(clazz);
  }

  @SuppressWarnings("unchecked")
  protected <T extends ParseTree> void forEachChild(ParserRuleContext context, Class<T> clazz,
      Function<T, Void> function) {
    if (context.children != null) {
      for (ParseTree child : context.children) {
        if (clazz.isInstance(child)) {
          function.apply((T) child);
        }
      }
    }
  }

  protected void forEachChild(ParserRuleContext context, Function<ParseTree, Void> function) {
    if (context.children != null) {
      for (ParseTree child : context.children) {
        function.apply(child);
      }
    }
  }

  @SuppressWarnings("unchecked")
  protected <T extends ParseTree> T getChild(ParserRuleContext context, Class<T> clazz) {
    if (context.children != null) {
      for (ParseTree child : context.children) {
        if (clazz.isInstance(child)) {
          return (T) child;
        }
      }
    }
    return null;
  }

  @SuppressWarnings("unchecked")
  protected <T extends ParserRuleContext> List<T> getChildren(ParserRuleContext context,
      Class<T> clazz) {
    List<T> list = new ArrayList<T>();
    if (context.children != null) {
      for (ParseTree child : context.children) {
        if (clazz.isInstance(child)) {
          list.add((T) child);
        }
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
        node.setStartColumn(context.getStart().getCharPositionInLine());
      } else if (startNode instanceof TerminalNode) {
        TerminalNode terminal = (TerminalNode) startNode;
        node.setStartLine(terminal.getSymbol().getLine());
        node.setStartColumn(terminal.getSymbol().getCharPositionInLine());
      }

      if (endNode instanceof ParserRuleContext) {
        ParserRuleContext context = (ParserRuleContext) endNode;
        node.setEndLine(context.getStop().getLine());
        node.setEndColumn(context.getStop().getCharPositionInLine()
            + context.getStop().getText().length() - 1);
      } else if (endNode instanceof TerminalNode) {
        TerminalNode terminal = (TerminalNode) endNode;
        node.setEndLine(terminal.getSymbol().getLine());
        node.setEndColumn(terminal.getSymbol().getCharPositionInLine()
            + terminal.getSymbol().getText().length() - 1);
      }
    }
  }

  protected <T extends ParseTree, S> List<S> transform(ParserRuleContext context, Class<T> clazz,
      Function<T, S> function) {
    List<S> list = new ArrayList<S>();
    if (context.children != null) {
      for (ParseTree child : context.children) {
        if (clazz.isInstance(child)) {
          @SuppressWarnings("unchecked")
          S result = function.apply((T) child);
          if (result != null) {
            list.add(result);
          }
        }
      }
    }
    return list;
  }

  protected <T extends ParseTree, S> S transformOne(ParserRuleContext context, Class<T> clazz,
      Function<T, S> function) {
    if (context.children != null) {
      for (ParseTree child : context.children) {
        if (clazz.isInstance(child)) {
          @SuppressWarnings("unchecked")
          S result = function.apply((T) child);
          if (result != null) {
            return result;
          }
        }
      }
    }
    return null;
  }
}
