package com.bacoder.parser.javaproperties.adapter;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.KeyContext;
import com.bacoder.parser.javaproperties.api.Key;

public class KeyAdapter extends Adapter<KeyContext, Key> {

  public KeyAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Key adapt(KeyContext context) {
    Key key = createData(context);
    key.setText(context.getText());
    return key;
  }
}
