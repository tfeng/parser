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
