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
package com.bacoder.parser.javaproperties.api;

import java.util.Collections;
import java.util.List;

import com.bacoder.parser.core.Node;

public class Properties extends Node {

  private List<Comment> comments = Collections.emptyList();

  private List<KeyValue> keyValues = Collections.emptyList();

  public List<Comment> getComments() {
    return comments;
  }

  public List<KeyValue> getKeyValues() {
    return keyValues;
  }

  public void setComments(List<Comment> comments) {
    this.comments = comments;
  }

  public void setKeyValues(List<KeyValue> keyValues) {
    this.keyValues = keyValues;
  }
}
