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
import com.bacoder.parser.java.api.MemberDeclaration;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.ClassDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.ConstructorDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.EnumDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.FieldDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.GenericConstructorDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.GenericMethodDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.InterfaceDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.MemberDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.MethodDeclarationContext;

public class MemberDeclarationAdapter
    extends JavaAdapter<MemberDeclarationContext, MemberDeclaration> {

  public MemberDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public MemberDeclaration adapt(MemberDeclarationContext context) {
    MethodDeclarationContext methodDeclarationContext =
        getChild(context, MethodDeclarationContext.class);
    if (methodDeclarationContext != null) {
      return getAdapter(MethodDeclarationAdapter.class).adapt(methodDeclarationContext);
    }

    GenericMethodDeclarationContext genericMethodDeclarationContext =
        getChild(context, GenericMethodDeclarationContext.class);
    if (genericMethodDeclarationContext != null) {
      return getAdapter(GenericMethodDeclarationAdapter.class).adapt(
          genericMethodDeclarationContext);
    }

    FieldDeclarationContext fieldDeclarationContext =
        getChild(context, FieldDeclarationContext.class);
    if (fieldDeclarationContext != null) {
      return getAdapter(FieldDeclarationAdapter.class).adapt(fieldDeclarationContext);
    }

    ConstructorDeclarationContext constructorDeclarationContext =
        getChild(context, ConstructorDeclarationContext.class);
    if (constructorDeclarationContext != null) {
      return getAdapter(ConstructorDeclarationAdapter.class).adapt(constructorDeclarationContext);
    }

    GenericConstructorDeclarationContext genericConstructorDeclarationContext =
        getChild(context, GenericConstructorDeclarationContext.class);
    if (genericConstructorDeclarationContext != null) {
      return getAdapter(GenericConstructorDeclarationAdapter.class).adapt(
          genericConstructorDeclarationContext);
    }

    InterfaceDeclarationContext interfaceDeclarationContext =
        getChild(context, InterfaceDeclarationContext.class);
    if (interfaceDeclarationContext != null) {
      return getAdapter(InterfaceDeclarationAdapter.class).adapt(interfaceDeclarationContext);
    }

    AnnotationTypeDeclarationContext annotationTypeDeclarationContext =
        getChild(context, AnnotationTypeDeclarationContext.class);
    if (annotationTypeDeclarationContext != null) {
      return getAdapter(AnnotationTypeDeclarationAdapter.class).adapt(
          annotationTypeDeclarationContext);
    }

    ClassDeclarationContext classDeclarationContext =
        getChild(context, ClassDeclarationContext.class);
    if (classDeclarationContext != null) {
      return getAdapter(ClassDeclarationAdapter.class).adapt(classDeclarationContext);
    }

    EnumDeclarationContext enumDeclarationContext = getChild(context, EnumDeclarationContext.class);
    if (enumDeclarationContext != null) {
      return getAdapter(EnumDeclarationAdapter.class).adapt(enumDeclarationContext);
    }

    return null;
  }
}
