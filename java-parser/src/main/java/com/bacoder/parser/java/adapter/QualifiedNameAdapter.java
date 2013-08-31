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

import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.JavaParser.QualifiedNameContext;
import com.bacoder.parser.java.api.Identifier;
import com.bacoder.parser.java.api.QualifiedName;
import com.google.common.base.Function;

public class QualifiedNameAdapter extends JavaAdapter<QualifiedNameContext, QualifiedName> {

  public QualifiedNameAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public QualifiedName adapt(QualifiedNameContext context) {
    QualifiedName qualifiedName = createNode(context);

    List<Identifier> identifiers =
        transform(context, TerminalNode.class, new Function<TerminalNode, Identifier>() {
          @Override
          public Identifier apply(TerminalNode input) {
            if (input.getSymbol().getType() == JavaParser.DOT) {
              return null;
            } else {
              return getAdapter(IdentifierAdapter.class).adapt(input);
            }
          }
        });
    qualifiedName.setIdentifiers(identifiers);

    return qualifiedName;
  }
}
