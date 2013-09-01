package com.bacoder.parser.java.adapter;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.api.ArrayDimension;
import com.google.common.base.Function;

public class ArrayDimensionsAdapter extends JavaAdapter<ParserRuleContext, List<ArrayDimension>> {

  public ArrayDimensionsAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<ArrayDimension> adapt(ParserRuleContext context) {
    final List<ArrayDimension> dimensions = new ArrayList<ArrayDimension>();
    forEachChild(context, TerminalNode.class, new Function<TerminalNode, Void>() {
      @Override
      public Void apply(TerminalNode terminal) {
        switch (terminal.getSymbol().getType()) {
        case JavaParser.LBRACK:
          dimensions.add(createNode(terminal, ArrayDimension.class));
          break;
        case JavaParser.RBRACK:
          if (dimensions.size() > 0) {
            setNodeAttributes(dimensions.get(dimensions.size() - 1), null, terminal);
          }
          break;
        default:
        }
        return null;
      }
    });
    return dimensions;
  }
}
