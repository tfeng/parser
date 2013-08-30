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
import com.bacoder.parser.java.api.CompilationUnit;
import com.bacoder.parser.java.api.ImportDeclaration;
import com.bacoder.parser.java.api.TypeDeclaration;
import com.google.common.base.Function;
import com.srctran.backend.parser.java.JavaParser.CompilationUnitContext;
import com.srctran.backend.parser.java.JavaParser.ImportDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.PackageDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeDeclarationContext;

public class CompilationUnitAdapter extends JavaAdapter<CompilationUnitContext, CompilationUnit> {

  public CompilationUnitAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public CompilationUnit adapt(CompilationUnitContext context) {
    CompilationUnit compilationUnit = createNode(context);

    PackageDeclarationContext packageDeclarationContext =
        getChild(context, PackageDeclarationContext.class);
    if (packageDeclarationContext != null) {
      compilationUnit.setPackageDeclaration(
          getAdapter(PackageDeclarationAdapter.class).adapt(packageDeclarationContext));
    }

    List<ImportDeclaration> importDeclarationas =
        transform(context, ImportDeclarationContext.class, new Function<ImportDeclarationContext,
            ImportDeclaration>() {
          @Override
          public ImportDeclaration apply(ImportDeclarationContext context) {
            return getAdapter(ImportDeclarationAdapter.class).adapt(context);
          }
        });
    compilationUnit.setImportDeclarations(importDeclarationas);

    List<TypeDeclaration> typeDeclarations =
        transform(context, TypeDeclarationContext.class, new Function<TypeDeclarationContext,
            TypeDeclaration>() {
          @Override
          public TypeDeclaration apply(TypeDeclarationContext context) {
            return getAdapter(TypeDeclarationAdapter.class).adapt(context);
          }
        });
    compilationUnit.setTypeDeclarations(typeDeclarations);

    return compilationUnit;
  }
}
