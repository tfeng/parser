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
    setAdapter(AnnotationConstantRestAdapter.class, new AnnotationConstantRestAdapter(this));
    setAdapter(AnnotationMethodRestAdapter.class, new AnnotationMethodRestAdapter(this));
    setAdapter(AnnotationTypeDeclarationAdapter.class, new AnnotationTypeDeclarationAdapter(this));
    setAdapter(AnnotationTypeElementDeclarationAdapter.class,
        new AnnotationTypeElementDeclarationAdapter(this));
    setAdapter(ArgumentsAdapter.class, new ArgumentsAdapter(this));
    setAdapter(ArrayDimensionsAdapter.class, new ArrayDimensionsAdapter(this));
    setAdapter(ArrayInitializerAdapter.class, new ArrayInitializerAdapter(this));
    setAdapter(BlockAdapter.class, new BlockAdapter(this));
    setAdapter(BlockStatementAdapter.class, new BlockStatementAdapter(this));
    setAdapter(ClassBodyAdapter.class, new ClassBodyAdapter(this));
    setAdapter(ClassBodyDeclarationAdapter.class, new ClassBodyDeclarationAdapter(this));
    setAdapter(ClassDeclarationAdapter.class, new ClassDeclarationAdapter(this));
    setAdapter(ClassOrInterfaceTypeAdapter.class, new ClassOrInterfaceTypeAdapter(this));
    setAdapter(CompilationUnitAdapter.class, new CompilationUnitAdapter(this));
    setAdapter(ConstDeclarationAdapter.class, new ConstDeclarationAdapter(this));
    setAdapter(ConstDeclaratorAdapter.class, new ConstDeclaratorAdapter(this));
    setAdapter(ConstructorDeclarationAdapter.class, new ConstructorDeclarationAdapter(this));
    setAdapter(ElementValueAdapter.class, new ElementValueAdapter(this));
    setAdapter(ElementValueArrayInitializerAdapter.class,
        new ElementValueArrayInitializerAdapter(this));
    setAdapter(ElementValuePairAdapter.class, new ElementValuePairAdapter(this));
    setAdapter(EnumConstantAdapter.class, new EnumConstantAdapter(this));
    setAdapter(EnumDeclarationAdapter.class, new EnumDeclarationAdapter(this));
    setAdapter(ExplicitGenericInvocationSuffixAdapter.class,
        new ExplicitGenericInvocationSuffixAdapter(this));
    setAdapter(ExpressionAdapter.class, new ExpressionAdapter(this));
    setAdapter(ExpressionListAdapter.class, new ExpressionListAdapter(this));
    setAdapter(FieldDeclarationAdapter.class, new FieldDeclarationAdapter(this));
    setAdapter(FormalParameterAdapter.class, new FormalParameterAdapter(this));
    setAdapter(FormalParametersAdapter.class, new FormalParametersAdapter(this));
    setAdapter(GenericConstructorDeclarationAdapter.class,
        new GenericConstructorDeclarationAdapter(this));
    setAdapter(GenericInterfaceMethodDeclarationAdapter.class,
        new GenericInterfaceMethodDeclarationAdapter(this));
    setAdapter(GenericMethodDeclarationAdapter.class, new GenericMethodDeclarationAdapter(this));
    setAdapter(IdentifierAdapter.class, new IdentifierAdapter(this));
    setAdapter(ImportDeclarationAdapter.class, new ImportDeclarationAdapter(this));
    setAdapter(InterfaceBodyDeclarationAdapter.class, new InterfaceBodyDeclarationAdapter(this));
    setAdapter(InterfaceDeclarationAdapter.class, new InterfaceDeclarationAdapter(this));
    setAdapter(InterfaceMethodDeclarationAdapter.class,
        new InterfaceMethodDeclarationAdapter(this));
    setAdapter(LocalVariableDeclarationAdapter.class, new LocalVariableDeclarationAdapter(this));
    setAdapter(MemberDeclarationAdapter.class, new MemberDeclarationAdapter(this));
    setAdapter(MethodDeclarationAdapter.class, new MethodDeclarationAdapter(this));
    setAdapter(NonWildcardTypeArgumentsAdapter.class, new NonWildcardTypeArgumentsAdapter(this));
    setAdapter(PackageDeclarationAdapter.class, new PackageDeclarationAdapter(this));
    setAdapter(PrimaryExpressionAdapter.class, new PrimaryExpressionAdapter(this));
    setAdapter(PrimitiveTypeAdapter.class, new PrimitiveTypeAdapter(this));
    setAdapter(QualifiedNameAdapter.class, new QualifiedNameAdapter(this));
    setAdapter(QualifiedNamesAdapter.class, new QualifiedNamesAdapter(this));
    setAdapter(StatementAdapter.class, new StatementAdapter(this));
    setAdapter(SuperSuffixAdapter.class, new SuperSuffixAdapter(this));
    setAdapter(TypeAdapter.class, new TypeAdapter(this));
    setAdapter(TypeArgumentAdapter.class, new TypeArgumentAdapter(this));
    setAdapter(TypeArgumentsAdapter.class, new TypeArgumentsAdapter(this));
    setAdapter(TypeDeclarationAdapter.class, new TypeDeclarationAdapter(this));
    setAdapter(TypeListAdapter.class, new TypeListAdapter(this));
    setAdapter(TypeParameterAdapter.class, new TypeParameterAdapter(this));
    setAdapter(TypeParametersAdapter.class, new TypeParametersAdapter(this));
    setAdapter(VariableDeclaratorAdapter.class, new VariableDeclaratorAdapter(this));
    setAdapter(VariableDeclaratorsAdapter.class, new VariableDeclaratorsAdapter(this));
    setAdapter(VariableFormalParameterAdapter.class, new VariableFormalParameterAdapter(this));
    setAdapter(VariableInitializerAdapter.class, new VariableInitializerAdapter(this));
  }
}
