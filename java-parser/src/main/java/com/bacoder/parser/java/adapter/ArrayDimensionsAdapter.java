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
