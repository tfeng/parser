package com.bacoder.parser.java.adapter;

import java.util.List;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.java.api.Type;
import com.srctran.backend.parser.java.JavaParser.NonWildcardTypeArgumentsContext;
import com.srctran.backend.parser.java.JavaParser.TypeListContext;

public class NonWildcardTypeArgumentsAdapter
    extends JavaAdapter<NonWildcardTypeArgumentsContext, List<Type>> {

  public NonWildcardTypeArgumentsAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public List<Type> adapt(NonWildcardTypeArgumentsContext context) {
    TypeListContext typeListContext = getChild(context, TypeListContext.class);
    if (typeListContext != null) {
      return getAdapter(TypeListAdapter.class).adapt(typeListContext);
    }

    return null;
  }
}
