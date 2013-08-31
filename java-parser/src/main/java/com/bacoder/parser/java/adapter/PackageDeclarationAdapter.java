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

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.JavaParser.AnnotationContext;
import com.bacoder.parser.java.JavaParser.PackageDeclarationContext;
import com.bacoder.parser.java.JavaParser.QualifiedNameContext;
import com.bacoder.parser.java.api.Annotation;
import com.bacoder.parser.java.api.PackageDeclaration;
import com.google.common.base.Function;

public class PackageDeclarationAdapter
    extends JavaAdapter<PackageDeclarationContext, PackageDeclaration> {

  public PackageDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public PackageDeclaration adapt(PackageDeclarationContext context) {
    PackageDeclaration packageDeclaration = createNode(context);

    List<Annotation> annotations =
        transform(context, AnnotationContext.class, new Function<AnnotationContext, Annotation>() {
          @Override
          public Annotation apply(AnnotationContext context) {
            return getAdapter(AnnotationAdapter.class).adapt(context);
          }
        });
    packageDeclaration.setAnnotations(annotations);

    QualifiedNameContext qualifiedNameContext = getChild(context, QualifiedNameContext.class);
    if (qualifiedNameContext != null) {
      packageDeclaration.setQualifiedName(
          getAdapter(QualifiedNameAdapter.class).adapt(qualifiedNameContext));
    }

    return packageDeclaration;
  }
}
