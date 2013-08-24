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
import com.bacoder.parser.java.api.AnnotationMemberDeclaration;
import com.bacoder.parser.java.api.AnnotationMethodDeclaration;
import com.bacoder.parser.java.api.NodeWithModifiers;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser.AnnotationConstantRestContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationMethodOrConstantRestContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationMethodRestContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeElementDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.AnnotationTypeElementRestContext;
import com.srctran.backend.parser.java.JavaParser.ClassDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.EnumDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.InterfaceDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;

public class AnnotationTypeElementDeclarationAdapter
    extends JavaAdapter<AnnotationTypeElementDeclarationContext, AnnotationMemberDeclaration> {

  public AnnotationTypeElementDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public AnnotationMemberDeclaration adapt(AnnotationTypeElementDeclarationContext context) {
    AnnotationMemberDeclaration annotationMemberDeclaration = null;

    AnnotationTypeElementRestContext annotationTypeElementRestContext =
        getChild(context, AnnotationTypeElementRestContext.class);
    if (annotationTypeElementRestContext != null) {
      TypeContext typeContext = getChild(annotationTypeElementRestContext, TypeContext.class);
      if (typeContext != null) {
        Type type = getAdapter(TypeAdapter.class).adapt(typeContext);

        AnnotationMethodOrConstantRestContext annotationMethodOrConstantRestContext =
            getChild(annotationTypeElementRestContext, AnnotationMethodOrConstantRestContext.class);
        if (annotationMethodOrConstantRestContext != null) {
          AnnotationMethodRestContext annotationMethodRestContext =
              getChild(annotationMethodOrConstantRestContext, AnnotationMethodRestContext.class);
          if (annotationMethodRestContext != null) {
            AnnotationMethodDeclaration annotationMethodDeclaration =
                getAdapter(AnnotationMethodRestAdapter.class).adapt(annotationMethodRestContext);
            annotationMethodDeclaration.setType(type);
            annotationMemberDeclaration = annotationMethodDeclaration;
          }

          AnnotationConstantRestContext annotationConstantRestContext =
              getChild(annotationMethodOrConstantRestContext, AnnotationConstantRestContext.class);
          if (annotationConstantRestContext != null) {
            annotationMemberDeclaration =
                getAdapter(AnnotationConstantRestAdapter.class).adapt(annotationConstantRestContext,
                    type, typeContext);
          }
        }
      }

      ClassDeclarationContext classDeclarationContext =
          getChild(context, ClassDeclarationContext.class);
      if (classDeclarationContext != null) {
        annotationMemberDeclaration =
            getAdapter(ClassDeclarationAdapter.class).adapt(classDeclarationContext);
      }

      InterfaceDeclarationContext interfaceDeclarationContext =
          getChild(context, InterfaceDeclarationContext.class);
      if (interfaceDeclarationContext != null) {
        annotationMemberDeclaration =
            getAdapter(InterfaceDeclarationAdapter.class).adapt(interfaceDeclarationContext);
      }

      EnumDeclarationContext enumDeclarationContext =
          getChild(context, EnumDeclarationContext.class);
      if (enumDeclarationContext != null) {
        annotationMemberDeclaration =
            getAdapter(EnumDeclarationAdapter.class).adapt(enumDeclarationContext);
      }

      AnnotationTypeDeclarationContext annotationTypeDeclarationContext =
          getChild(context, AnnotationTypeDeclarationContext.class);
      if (annotationTypeDeclarationContext != null) {
        annotationMemberDeclaration =
            getAdapter(AnnotationTypeDeclarationAdapter.class).adapt(
                annotationTypeDeclarationContext);
      }
    }

    if (annotationMemberDeclaration != null) {
      setModifiers(context, (NodeWithModifiers) annotationMemberDeclaration);
    }

    return annotationMemberDeclaration;
  }
}
