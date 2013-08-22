package com.bacoder.parser.javaproperties.api;

import com.bacoder.parser.core.TextNode;

public abstract class KeyOrValueNode extends TextNode {

  private static final String[][] SPECIAL_CHAR_MAP = {
    {"\\ ", " "},
    {"\\\t", "\t"},
    {"\\\f", "\f"},
    {"\\\r", "\r"},
    {"\\\n", "\n"},
    {"\n", ""},
    {"\r\n", ""},
    {"\r", ""},
    {"\\u1234", "\u1234"}
  };

  public String getSanitizedText() {
    String result = getText();
    for (String[] part : SPECIAL_CHAR_MAP) {
      result = result.replace(part[0], part[1]);
    }
    return result;
  }
}
