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
package com.bacoder.parser.java.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Visitors;

public class CompilationUnit extends JavaNode {

  private List<ImportDeclaration> importDeclarations = Collections.emptyList();

  private PackageDeclaration packageDeclaration;

  private List<TypeDeclaration> typeDeclarations = Collections.emptyList();

  public List<ImportDeclaration> getImportDeclarations() {
    return importDeclarations;
  }

  public PackageDeclaration getPackageDeclaration() {
    return packageDeclaration;
  }

  public List<TypeDeclaration> getTypeDeclarations() {
    return typeDeclarations;
  }

  public void setImportDeclarations(List<ImportDeclaration> importDeclarations) {
    this.importDeclarations = importDeclarations;
  }

  public void setPackageDeclaration(PackageDeclaration packageDeclaration) {
    this.packageDeclaration = packageDeclaration;
  }

  public void setTypeDeclarations(List<TypeDeclaration> typeDeclarations) {
    this.typeDeclarations = typeDeclarations;
  }

  @Override
  protected void visitChildren(Visitors visitors) {
    if (packageDeclaration != null) {
      packageDeclaration.visit(visitors);
    }
    for (ImportDeclaration importDeclaration : importDeclarations) {
      importDeclaration.visit(visitors);
    }
    for (TypeDeclaration typeDeclaration : typeDeclarations) {
      typeDeclaration.visit(visitors);
    }
  }
}
