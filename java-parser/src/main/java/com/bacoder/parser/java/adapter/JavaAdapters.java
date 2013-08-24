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

public class JavaAdapters extends Adapters {

  public JavaAdapters() {
    setAdapter(AnnotationAdapter.class, new AnnotationAdapter(this));
    setAdapter(BlockAdapter.class, new BlockAdapter(this));
    setAdapter(BlockStatementAdapter.class, new BlockStatementAdapter(this));
    setAdapter(ClassBodyAdapter.class, new ClassBodyAdapter(this));
    setAdapter(ClassBodyDeclarationAdapter.class, new ClassBodyDeclarationAdapter(this));
    setAdapter(ClassDeclarationAdapter.class, new ClassDeclarationAdapter(this));
    setAdapter(ClassOrInterfaceTypeAdapter.class, new ClassOrInterfaceTypeAdapter(this));
    setAdapter(CompilationUnitAdapter.class, new CompilationUnitAdapter(this));
    setAdapter(ElementValueAdapter.class, new ElementValueAdapter(this));
    setAdapter(ElementValueArrayInitializerAdapter.class, new ElementValueArrayInitializerAdapter(this));
    setAdapter(ElementValuePairAdapter.class, new ElementValuePairAdapter(this));
    setAdapter(ExpressionAdapter.class, new ExpressionAdapter(this));
    setAdapter(FormalParameterAdapter.class, new FormalParameterAdapter(this));
    setAdapter(FormalParametersAdapter.class, new FormalParametersAdapter(this));
    setAdapter(IdentifierAdapter.class, new IdentifierAdapter(this));
    setAdapter(ImportDeclarationAdapter.class, new ImportDeclarationAdapter(this));
    setAdapter(MemberDeclarationAdapter.class, new MemberDeclarationAdapter(this));
    setAdapter(MethodDeclarationAdapter.class, new MethodDeclarationAdapter(this));
    setAdapter(PackageDeclarationAdapter.class, new PackageDeclarationAdapter(this));
    setAdapter(PrimitiveTypeAdapter.class, new PrimitiveTypeAdapter(this));
    setAdapter(QualifiedNameAdapter.class, new QualifiedNameAdapter(this));
    setAdapter(QualifiedNamesAdapter.class, new QualifiedNamesAdapter(this));
    setAdapter(TypeAdapter.class, new TypeAdapter(this));
    setAdapter(TypeArgumentAdapter.class, new TypeArgumentAdapter(this));
    setAdapter(TypeArgumentsAdapter.class, new TypeArgumentsAdapter(this));
    setAdapter(TypeDeclarationAdapter.class, new TypeDeclarationAdapter(this));
    setAdapter(TypeListAdapter.class, new TypeListAdapter(this));
    setAdapter(TypeParameterAdapter.class, new TypeParameterAdapter(this));
    setAdapter(TypeParametersAdapter.class, new TypeParametersAdapter(this));
    setAdapter(VariableFormalParameterAdapter.class, new VariableFormalParameterAdapter(this));
  }
}
