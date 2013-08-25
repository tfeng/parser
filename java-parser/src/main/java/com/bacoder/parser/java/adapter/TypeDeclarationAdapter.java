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
import com.bacoder.parser.java.api.AnnotationDeclaration;
import com.bacoder.parser.java.api.ClassDeclaration;
import com.bacoder.parser.java.api.EnumDeclaration;
import com.bacoder.parser.java.api.InterfaceDeclaration;
import com.bacoder.parser.java.api.TypeDeclaration;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.ClassDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.EnumDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.InterfaceDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeDeclarationContext;

public class TypeDeclarationAdapter extends JavaAdapter<TypeDeclarationContext, TypeDeclaration> {

  public TypeDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public TypeDeclaration adapt(TypeDeclarationContext context) {
    ClassDeclarationContext classDeclarationContext =
        getChild(context, ClassDeclarationContext.class);
    if (classDeclarationContext != null) {
      ClassDeclaration classDeclaration =
          getAdapter(ClassDeclarationAdapter.class).adapt(classDeclarationContext);
      setNodeAttributes(classDeclaration, context);
      setClassOrInterfaceModifiers(context, classDeclaration);
      return classDeclaration;
    }

    EnumDeclarationContext enumDeclarationContext = getChild(context, EnumDeclarationContext.class);
    if (enumDeclarationContext != null) {
      EnumDeclaration enumDeclaration =
          getAdapter(EnumDeclarationAdapter.class).adapt(enumDeclarationContext);
      setClassOrInterfaceModifiers(context, enumDeclaration);
      return enumDeclaration;
    }

    InterfaceDeclarationContext interfaceDeclarationContext =
        getChild(context, InterfaceDeclarationContext.class);
    if (interfaceDeclarationContext != null) {
      InterfaceDeclaration interfaceDeclaration =
          getAdapter(InterfaceDeclarationAdapter.class).adapt(interfaceDeclarationContext);
      setClassOrInterfaceModifiers(context, interfaceDeclaration);
      return interfaceDeclaration;
    }

    AnnotationTypeDeclarationContext annotationTypeDeclarationContext =
        getChild(context, AnnotationTypeDeclarationContext.class);
    if (annotationTypeDeclarationContext != null) {
      AnnotationDeclaration annotationDeclaration =
          getAdapter(AnnotationTypeDeclarationAdapter.class).adapt(
              annotationTypeDeclarationContext);
      setClassOrInterfaceModifiers(context, annotationDeclaration);
      return annotationDeclaration;
    }

    return null;
  }
}
