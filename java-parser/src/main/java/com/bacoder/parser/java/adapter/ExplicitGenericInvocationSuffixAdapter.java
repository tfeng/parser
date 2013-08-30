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

import java.util.Collections;
import java.util.List;

import org.antlr.v4.runtime.tree.TerminalNode;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.Invocation;
import com.bacoder.parser.java.api.MethodInvocation;
import com.bacoder.parser.java.api.SuperInvocation;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.ArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.ExplicitGenericInvocationSuffixContext;
import com.srctran.backend.parser.java.JavaParser.SuperSuffixContext;

public class ExplicitGenericInvocationSuffixAdapter
    extends JavaAdapter<ExplicitGenericInvocationSuffixContext, Invocation> {

  public ExplicitGenericInvocationSuffixAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Invocation adapt(ExplicitGenericInvocationSuffixContext context) {
    return adapt(context, Collections.<Type>emptyList());
  }

  public Invocation adapt(ExplicitGenericInvocationSuffixContext context, List<Type> typeList) {
    TerminalNode firstTerminal = getChild(context, TerminalNode.class);
    if (firstTerminal != null) {
      switch (firstTerminal.getSymbol().getType()) {
      case JavaParser.SUPER: {
        SuperInvocation superInvocation = createNode(context, SuperInvocation.class);
        superInvocation.setTypeArguments(typeList);

        SuperSuffixContext superSuffixContext = getChild(context, SuperSuffixContext.class);
        if (superSuffixContext != null) {
          superInvocation =
              getAdapter(SuperSuffixAdapter.class).adapt(superSuffixContext, superInvocation);
        }

        return superInvocation;
      }
      case JavaParser.Identifier: {
        MethodInvocation methodInvocation = createNode(context, MethodInvocation.class);
        methodInvocation.setTypeArguments(typeList);

        methodInvocation.setName(getAdapter(IdentifierAdapter.class).adapt(firstTerminal));

        ArgumentsContext argumentsContext = getChild(context, ArgumentsContext.class);
        if (argumentsContext != null) {
          methodInvocation.setArguments(
              getAdapter(ArgumentsAdapter.class).adapt(argumentsContext));
        }

        return methodInvocation;
      }
      default:
      }
    }

    return null;
  }
}
