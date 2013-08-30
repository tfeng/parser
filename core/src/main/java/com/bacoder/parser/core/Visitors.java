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

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

public class Visitors {

  private static class Comparator implements java.util.Comparator<Class<? extends Node>> {
    @Override
    public int compare(Class<? extends Node> class1, Class<? extends Node> class2) {
      if (class1.isAssignableFrom(class2)) {
        return 1;
      } else if (class2.isAssignableFrom(class1)) {
        return -1;
      } else {
        return 0;
      }
    }
  }

  private Map<Class<? extends Node>, Visitor<? extends Node>> visitors =
      new TreeMap<Class<? extends Node>, Visitor<? extends Node>>(new Comparator());

  public Visitors() {
  }

  public <T extends Node> Visitors(Class<T> nodeClass, Visitor<T> visitor) {
    addVisitor(nodeClass, visitor);
  }

  public <T extends Node> void addVisitor(Class<T> nodeClass, Visitor<T> visitor) {
    visitors.put(nodeClass, visitor);
  }

  @SuppressWarnings("unchecked")
  public <T extends Node> void visitAfter(T node) {
    Class<? extends Object> clazz = node.getClass();
    for (Entry<Class<? extends Node>, Visitor<? extends Node>> entry : visitors.entrySet()) {
      if (entry.getKey().isAssignableFrom(clazz)) {
        ((Visitor<T>) entry.getValue()).visitAfter(node);
      }
    }
  }

  @SuppressWarnings("unchecked")
  public <T extends Node> void visitBefore(T node) {
    Class<? extends Object> clazz = node.getClass();
    for (Entry<Class<? extends Node>, Visitor<? extends Node>> entry : visitors.entrySet()) {
      if (entry.getKey().isAssignableFrom(clazz)) {
        ((Visitor<T>) entry.getValue()).visitBefore(node);
      }
    }
  }
}
