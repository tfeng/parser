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
import com.bacoder.parser.java.JavaParser.AnnotationTypeDeclarationContext;
import com.bacoder.parser.java.JavaParser.ClassDeclarationContext;
import com.bacoder.parser.java.JavaParser.ConstDeclarationContext;
import com.bacoder.parser.java.JavaParser.EnumDeclarationContext;
import com.bacoder.parser.java.JavaParser.GenericInterfaceMethodDeclarationContext;
import com.bacoder.parser.java.JavaParser.InterfaceBodyDeclarationContext;
import com.bacoder.parser.java.JavaParser.InterfaceDeclarationContext;
import com.bacoder.parser.java.JavaParser.InterfaceMemberDeclarationContext;
import com.bacoder.parser.java.JavaParser.InterfaceMethodDeclarationContext;
import com.bacoder.parser.java.api.InterfaceMemberDeclaration;
import com.bacoder.parser.java.api.NodeWithModifiers;

public class InterfaceBodyDeclarationAdapter
    extends JavaAdapter<InterfaceBodyDeclarationContext, InterfaceMemberDeclaration> {

  public InterfaceBodyDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public InterfaceMemberDeclaration adapt(InterfaceBodyDeclarationContext context) {
    InterfaceMemberDeclarationContext interfaceMemberDeclarationContext =
        getChild(context, InterfaceMemberDeclarationContext.class);
    if (interfaceMemberDeclarationContext == null) {
      return null;
    }

    InterfaceMemberDeclaration interfaceMemberDeclaration = null;

    ConstDeclarationContext constDeclarationContext =
        getChild(interfaceMemberDeclarationContext, ConstDeclarationContext.class);
    if (constDeclarationContext != null) {
      interfaceMemberDeclaration =
          getAdapter(ConstDeclarationAdapter.class).adapt(constDeclarationContext);
    }

    InterfaceMethodDeclarationContext interfaceMethodDeclarationContext =
        getChild(interfaceMemberDeclarationContext, InterfaceMethodDeclarationContext.class);
    if (interfaceMethodDeclarationContext != null) {
      interfaceMemberDeclaration =
          getAdapter(InterfaceMethodDeclarationAdapter.class).adapt(
              interfaceMethodDeclarationContext);
    }

    GenericInterfaceMethodDeclarationContext genericInterfaceMethodDeclarationContext =
        getChild(interfaceMemberDeclarationContext, GenericInterfaceMethodDeclarationContext.class);
    if (genericInterfaceMethodDeclarationContext != null) {
      interfaceMemberDeclaration =
          getAdapter(GenericInterfaceMethodDeclarationAdapter.class).adapt(
              genericInterfaceMethodDeclarationContext);
    }

    InterfaceDeclarationContext interfaceDeclarationContext =
        getChild(interfaceMemberDeclarationContext, InterfaceDeclarationContext.class);
    if (interfaceDeclarationContext != null) {
      interfaceMemberDeclaration =
          getAdapter(InterfaceDeclarationAdapter.class).adapt(interfaceDeclarationContext);
    }

    AnnotationTypeDeclarationContext annotationTypeContext =
        getChild(interfaceMemberDeclarationContext, AnnotationTypeDeclarationContext.class);
    if (annotationTypeContext != null) {
      interfaceMemberDeclaration =
          getAdapter(AnnotationTypeDeclarationAdapter.class).adapt(annotationTypeContext);
    }

    ClassDeclarationContext classDeclarationContext =
        getChild(interfaceMemberDeclarationContext, ClassDeclarationContext.class);
    if (classDeclarationContext != null) {
      interfaceMemberDeclaration =
          getAdapter(ClassDeclarationAdapter.class).adapt(classDeclarationContext);
    }

    EnumDeclarationContext enumDeclarationContext =
        getChild(interfaceMemberDeclarationContext, EnumDeclarationContext.class);
    if (enumDeclarationContext != null) {
      interfaceMemberDeclaration =
          getAdapter(EnumDeclarationAdapter.class).adapt(enumDeclarationContext);
    }

    if (interfaceMemberDeclaration != null) {
      setModifiers(context, (NodeWithModifiers) interfaceMemberDeclaration);
    }

    return interfaceMemberDeclaration;
  }
}
