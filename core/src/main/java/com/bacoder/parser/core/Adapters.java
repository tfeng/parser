package com.bacoder.parser.core;

import java.util.HashMap;
import java.util.Map;

public class Adapters {

  private Map<Class<? extends Adapter<?, ?>>, Adapter<?, ?>> adapters =
      new HashMap<Class<? extends Adapter<?, ?>>, Adapter<?, ?>>();

  @SuppressWarnings("unchecked")
  public <T extends Adapter<?, ?>> T getAdapter(Class<T> clazz) {
    return (T) adapters.get(clazz);
  }

  public <T extends Adapter<?, ?>> void setAdapter(Class<T> clazz, T adapter) {
    adapters.put(clazz, adapter);
  }
}
