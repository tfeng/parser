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

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser.BlockStatementContext;
import com.bacoder.parser.java.JavaParser.LocalVariableDeclarationContext;
import com.bacoder.parser.java.JavaParser.LocalVariableDeclarationStatementContext;
import com.bacoder.parser.java.JavaParser.StatementContext;
import com.bacoder.parser.java.JavaParser.TypeDeclarationContext;
import com.bacoder.parser.java.api.BlockStatement;
import com.bacoder.parser.java.api.LocalVariableDeclaration;

public class BlockStatementAdapter extends JavaAdapter<BlockStatementContext, BlockStatement> {

  public BlockStatementAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public BlockStatement adapt(BlockStatementContext context) {
    LocalVariableDeclarationStatementContext localVariableDeclarationStatementContext =
        getChild(context, LocalVariableDeclarationStatementContext.class);
    if (localVariableDeclarationStatementContext != null) {
      LocalVariableDeclarationContext localVariableDeclarationContext =
          getChild(localVariableDeclarationStatementContext, LocalVariableDeclarationContext.class);
      if (localVariableDeclarationContext != null) {
        LocalVariableDeclaration localVariableDeclaration =
            getAdapter(LocalVariableDeclarationAdapter.class).adapt(
                localVariableDeclarationContext);
        setNodeAttributes(localVariableDeclaration, localVariableDeclarationStatementContext);
        return localVariableDeclaration;
      }
    }

    StatementContext statementContext = getChild(context, StatementContext.class);
    if (statementContext != null) {
      return getAdapter(StatementAdapter.class).adapt(statementContext);
    }

    TypeDeclarationContext typeDeclarationContext = getChild(context, TypeDeclarationContext.class);
    if (typeDeclarationContext != null) {
      return getAdapter(TypeDeclarationAdapter.class).adapt(typeDeclarationContext);
    }

    return null;
  }
}
