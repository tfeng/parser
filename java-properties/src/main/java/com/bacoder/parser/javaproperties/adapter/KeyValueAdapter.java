package com.bacoder.parser.javaproperties.adapter;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.KeyContext;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.KeyValueContext;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.ValueContext;
import com.bacoder.parser.javaproperties.api.KeyValue;

public class KeyValueAdapter extends Adapter<KeyValueContext, KeyValue> {

  public KeyValueAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public KeyValue adapt(KeyValueContext context) {
    KeyContext keyContext = getChild(context, KeyContext.class);
    ValueContext valueContext = getChild(context, ValueContext.class);
    if (keyContext != null && valueContext != null) {
      KeyValue keyValue = createData(context);
      keyValue.setKey(getAdapter(KeyAdapter.class).adapt(keyContext));
      keyValue.setValue(getAdapter(ValueAdapter.class).adapt(valueContext));
      return keyValue;
    } else {
      return null;
    }
  }
}
