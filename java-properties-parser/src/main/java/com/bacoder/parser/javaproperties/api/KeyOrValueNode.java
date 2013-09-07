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

import org.apache.commons.lang.StringEscapeUtils;

import com.bacoder.parser.core.DumpTextWithToString;
import com.bacoder.parser.core.TextNode;

@DumpTextWithToString
public abstract class KeyOrValueNode extends TextNode {

  public String getSanitizedText() {
    String result = getText();

    if (unescapeNewLines()) {
      result = result.replaceAll("\\\\\n\\s*", "");
    }

    result = StringEscapeUtils.unescapeJava(result);

    if (!unescapeNewLines()) {
      result = result.replaceAll("\\\n\\s*", "");
    }

    return result;
  }

  @Override
  public String toString() {
    return getSanitizedText();
  }

  public abstract boolean unescapeNewLines();
}
