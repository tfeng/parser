package com.bacoder.parser.javaproperties.adapter;

import com.bacoder.parser.core.Adapter;
import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.ValueContext;
import com.bacoder.parser.javaproperties.api.Value;

public class ValueAdapter extends Adapter<ValueContext, Value> {

  public ValueAdapter(Adapters adapters) {
    super(adapters);
  }

  @Override
  public Value adapt(ValueContext context) {
    Value value = createData(context);
    value.setText(context.getText());
    return value;
  }
}
