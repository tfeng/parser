package com.bacoder.parser.java.adapter;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.LocalVariableDeclaration;
import com.srctran.backend.parser.java.JavaParser.LocalVariableDeclarationContext;
import com.srctran.backend.parser.java.JavaParser.TypeContext;
import com.srctran.backend.parser.java.JavaParser.VariableDeclaratorsContext;

public class LocalVariableDeclarationAdapter
    extends JavaAdapter<LocalVariableDeclarationContext, LocalVariableDeclaration> {

  public LocalVariableDeclarationAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public LocalVariableDeclaration adapt(LocalVariableDeclarationContext context) {
    LocalVariableDeclaration localVariableDeclaration = createData(context);

    setModifiers(context, localVariableDeclaration);

    TypeContext typeContext = getChild(context, TypeContext.class);
    if (typeContext != null) {
      localVariableDeclaration.setType(getAdapter(TypeAdapter.class).adapt(typeContext));
    }

    VariableDeclaratorsContext variableDeclaratorsContext =
        getChild(context, VariableDeclaratorsContext.class);
    if (variableDeclaratorsContext != null) {
      localVariableDeclaration.setVariableDeclarations(
          getAdapter(VariableDeclaratorsAdapter.class).adapt(variableDeclaratorsContext,
              localVariableDeclaration.getType(), typeContext));
    }

    return localVariableDeclaration;
  }
}
