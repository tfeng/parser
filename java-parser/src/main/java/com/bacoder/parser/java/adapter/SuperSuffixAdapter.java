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

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.SuperInvocation;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.SuperSuffixContext;

public class SuperSuffixAdapter extends JavaAdapter<SuperSuffixContext, SuperInvocation> {

  public SuperSuffixAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public SuperInvocation adapt(SuperSuffixContext context) {
    throw new RuntimeException("Not supported");
  }

  public SuperInvocation adapt(SuperSuffixContext context, SuperInvocation superInvocation) {
    TerminalNode identifierNode = getTerminalNode(context, JavaParser.Identifier);
    if (identifierNode != null) {
      superInvocation.setName(getAdapter(IdentifierAdapter.class).adapt(identifierNode));
    }

    ArgumentsContext argumentsContext = getChild(context, ArgumentsContext.class);
    if (argumentsContext != null) {
      superInvocation.setArguments(
          getAdapter(ArgumentsAdapter.class).adapt(argumentsContext));
    }

    return superInvocation;
  }
}
