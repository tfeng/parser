package com.bacoder.parser.javaproperties.adapter;

import com.bacoder.parser.core.Adapters;

public class PropertiesAdapters extends Adapters {

  public PropertiesAdapters() {
    setAdapter(CommentAdapter.class, new CommentAdapter(this));
    setAdapter(KeyAdapter.class, new KeyAdapter(this));
    setAdapter(KeyValueAdapter.class, new KeyValueAdapter(this));
    setAdapter(PropertiesAdapter.class, new PropertiesAdapter(this));
    setAdapter(ValueAdapter.class, new ValueAdapter(this));
  }
}
