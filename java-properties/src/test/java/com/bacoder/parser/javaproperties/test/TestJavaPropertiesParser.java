package com.bacoder.parser.javaproperties.test;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bacoder.parser.javaproperties.JavaPropertiesLexer;
import com.bacoder.parser.javaproperties.JavaPropertiesParser;
import com.bacoder.parser.javaproperties.JavaPropertiesParser.PropertiesContext;
import com.bacoder.parser.javaproperties.adapter.PropertiesAdapter;
import com.bacoder.parser.javaproperties.adapter.PropertiesAdapters;
import com.bacoder.parser.javaproperties.api.KeyValue;
import com.bacoder.parser.javaproperties.api.Properties;

@Test
public class TestJavaPropertiesParser {

  public void testEquals() {
    String input = "# Comment\nkey1=value1\nkey2 = value2\nkey3= value3\nkey4 =value4";
    Properties properties = parse(input);

    verify(properties, input);

    Assert.assertEquals(properties.getComments().size(), 1);
    Assert.assertEquals(properties.getComments().get(0).getText(), "# Comment");
    Assert.assertEquals(properties.getComments().get(0).getStartLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getStartIndex(), 0);
    Assert.assertEquals(properties.getComments().get(0).getEndLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getEndIndex(), "# Comment".length() - 1);
  }

  public void testColons() {
    String input = "# Comment: colon\nkey1:value1\nkey2 :value2\nkey3: value3\nkey4 : value4\nkey5:value5:";
    Properties properties = parse(input);

    verify(properties, input);

    Assert.assertEquals(properties.getComments().size(), 1);
    Assert.assertEquals(properties.getComments().get(0).getText(), "# Comment: colon");
    Assert.assertEquals(properties.getComments().get(0).getStartLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getStartIndex(), 0);
    Assert.assertEquals(properties.getComments().get(0).getEndLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getEndIndex(), "# Comment: colon".length() - 1);
  }

  public void testSpaces() {
    String input = "key1 value1\nkey2  value2\nkey3\tvalue3\nkey4\t value4\n";
    Properties properties = parse(input);

    verify(properties, input);

    Assert.assertEquals(properties.getComments().size(), 0);
  }

  public void testEqualsWithSpaces() {
    String input = "  # Comment\n  key1 = value1 \n key2 = value2  \n";
    Properties properties = parse(input);

    verify(properties, input);

    Assert.assertEquals(properties.getComments().size(), 1);
    Assert.assertEquals(properties.getComments().get(0).getText(), "# Comment");
    Assert.assertEquals(properties.getComments().get(0).getStartLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getStartIndex(), 2);
    Assert.assertEquals(properties.getComments().get(0).getEndLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getEndIndex(), "# Comment".length() + 1);
  }

  public void testEqualsWithTabs() {
    String input = "\t\t#\tComment\n\t\tkey1\t=\tvalue1\t\n\tkey2\t=\tvalue2\t\t\n";
    Properties properties = parse(input);

    verify(properties, input);

    Assert.assertEquals(properties.getComments().size(), 1);
    Assert.assertEquals(properties.getComments().get(0).getText(), "#\tComment");
    Assert.assertEquals(properties.getComments().get(0).getStartLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getStartIndex(), 2);
    Assert.assertEquals(properties.getComments().get(0).getEndLine(), 1);
    Assert.assertEquals(properties.getComments().get(0).getEndIndex(), "#\tComment".length() + 1);
  }

  public void testUnicode() {
    String input = "键1 = 值1\n键\\\t2 = 值2\n键\\\t3 = 值3\t 完 \nkey4 = val\\u1234ue4";
    Properties properties = parse(input);
    verify(properties, input);
  }

  public void testSpecialCharacters() {
    String input = "key1 = value1\f\nkey2 = value2\\\f\f\\\t\nkey3 = value3\\\nkey4 = value4\nkey5\\\t\\\n\\\r\\\n = value5";
    Properties properties = parse(input);
    verify(properties, input);
  }

  private JavaPropertiesParser getParser(String input) {
    JavaPropertiesLexer lexer = new JavaPropertiesLexer(new ANTLRInputStream(input));
    CommonTokenStream tokenStream = new CommonTokenStream(lexer);
    JavaPropertiesParser parser = new JavaPropertiesParser(tokenStream);
    return parser;
  }

  private Properties parse(String input) {
    JavaPropertiesParser parser = getParser(input);
    PropertiesContext propertiesContext = parser.properties();
    PropertiesAdapters adapters = new PropertiesAdapters();
    Properties properties = adapters.getAdapter(PropertiesAdapter.class).adapt(propertiesContext);
    return properties;
  }

  private void verify(Properties properties, String input) {
    java.util.Properties expected = new java.util.Properties();
    try {
      expected.load(
          new InputStreamReader(
              new ByteArrayInputStream(input.getBytes("UTF-8")), Charset.forName("UTF-8")));
    } catch (Exception e) {
      throw new RuntimeException("Unable to load properties" + e);
    }

    for (KeyValue keyValue : properties.getKeyValues()) {
      String key = replaceSpecialCharacters(keyValue.getKey().getText());
      Assert.assertTrue(expected.containsKey(key),
          "Key does not exist in expected Java properties");
      String value = replaceSpecialCharacters(keyValue.getValue().getText());
      Assert.assertEquals(value, expected.get(key),
          "Value does not match expected Java property value");
    }
  }

  private String replaceSpecialCharacters(String input) {
    String output = input;
    String[][] map =
      {
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
    for (String[] part : map) {
      output = output.replace(part[0], part[1]);
    }
    return output;
  }
}
