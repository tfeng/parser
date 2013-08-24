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
import com.bacoder.parser.java.api.BlockClassBodyDeclaration;
import com.bacoder.parser.java.api.ClassBodyDeclaration;
import com.bacoder.parser.java.api.EmptyClassBodyDeclaration;
import com.bacoder.parser.java.api.MemberClassBodyDeclaration;
import com.bacoder.parser.java.api.MemberDeclaration;
import com.srctran.backend.parser.java.JavaParser;
import com.srctran.backend.parser.java.JavaParser.BlockContext;
import com.srctran.backend.parser.java.JavaParser.ClassBodyDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.MemberDeclarationContext;

public class ClassBodyDeclarationAdapter
    extends JavaAdapter<ClassBodyDeclarationContext, ClassBodyDeclaration> {

  public ClassBodyDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public ClassBodyDeclaration adapt(ClassBodyDeclarationContext context) {
    BlockContext blockContext = getChild(context, BlockContext.class);
    MemberDeclarationContext memberDeclarationContext =
        getChild(context, MemberDeclarationContext.class);
    
    if (blockContext != null) {
      BlockClassBodyDeclaration blockClassBodyDeclaration =
          createData(BlockClassBodyDeclaration.class, blockContext);
      blockClassBodyDeclaration.setStatic(hasTerminalNode(context, JavaParser.STATIC));
      blockClassBodyDeclaration.setBlockStatements(
          getAdapter(BlockAdapter.class).adapt(blockContext));
      return blockClassBodyDeclaration;

    } else if (memberDeclarationContext != null) {
      MemberClassBodyDeclaration memberClassBodyDeclaration =
          createData(MemberClassBodyDeclaration.class, memberDeclarationContext);
      MemberDeclaration memberDeclaration =
          getAdapter(MemberDeclarationAdapter.class).adapt(memberDeclarationContext);
      setModifiers(context, memberDeclaration);
      memberClassBodyDeclaration.setMemberDeclaration(memberDeclaration);
      return memberClassBodyDeclaration;

    } else {
      EmptyClassBodyDeclaration emptyClassBodyDeclaration =
          createData(EmptyClassBodyDeclaration.class, context);
      return emptyClassBodyDeclaration;
    }
  }
}
