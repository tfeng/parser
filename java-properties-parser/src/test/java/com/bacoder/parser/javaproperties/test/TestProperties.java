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
package com.bacoder.parser.javaproperties.test;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.Charset;
import java.util.List;

import org.antlr.v4.runtime.BailErrorStrategy;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.CommonTokenFactory;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.UnbufferedCharStream;
import org.antlr.v4.runtime.UnbufferedTokenStream;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bacoder.parser.core.Adapters;
import com.bacoder.parser.core.Pair;
import com.bacoder.parser.javaproperties.JavaPropertiesLexer;
import com.bacoder.parser.javaproperties.JavaPropertiesParser;
import com.bacoder.parser.javaproperties.adapter.PropertiesAdapter;
import com.bacoder.parser.javaproperties.adapter.PropertiesAdapters;
import com.bacoder.parser.javaproperties.api.KeyValue;
import com.bacoder.parser.javaproperties.api.Properties;
import com.bacoder.parser.testutil.BaseTest;

@Test
public class TestProperties extends BaseTest {

  private static final Logger LOG = Logger.getLogger(TestProperties.class);

  public void test() throws IOException {
    List<Pair<File, File>> testCases = findTestCases("TestProperties", "properties");
    for (Pair<File, File> testCase : testCases) {
      LOG.info("Running test case " + testCase.getFirst().getParentFile().getName());
      Properties properties = getProperties(testCase.getFirst());
      verifyProperties(properties, testCase.getFirst());
      verifyAST(properties, testCase.getSecond());
    }
  }

  @Override
  protected Adapters getAdapters() {
    return new PropertiesAdapters();
  }

  protected Properties getProperties(File propertiesFile) throws IOException {
    InputStream inputStream = new FileInputStream(propertiesFile);
    try {
      CharStream stream = new UnbufferedCharStream(inputStream);
      JavaPropertiesLexer lexer = new JavaPropertiesLexer(stream);
      lexer.setTokenFactory(new CommonTokenFactory(true));
      UnbufferedTokenStream<Token> tokenStream = new UnbufferedTokenStream<Token>(lexer);
      JavaPropertiesParser parser = new JavaPropertiesParser(tokenStream);
      parser.setErrorHandler(new BailErrorStrategy());
      return getAdapter(PropertiesAdapter.class).adapt(parser.properties());
    } finally {
      inputStream.close();
    }
  }

  private void verifyProperties(Properties properties, File input) {
    java.util.Properties expected = new java.util.Properties();
    try {
      expected.load(new InputStreamReader(new FileInputStream(input), Charset.forName("UTF-8")));
    } catch (Exception e) {
      throw new RuntimeException("Unable to load properties" + e);
    }

    for (KeyValue keyValue : properties.getKeyValues()) {
      String key = keyValue.getKey().getSanitizedText();
      Assert.assertTrue(expected.containsKey(key),
          String.format("Key \"%s\" does not exist in expected Java properties", key));
      String value = keyValue.getValue().getSanitizedText();
      Assert.assertEquals(value, expected.get(key),
          String.format("Value \"%s\" does not match expected Java property value \"%s\"", value,
              expected.get(key)));
    }

    Assert.assertEquals(properties.getKeyValues().size(), expected.size());
  }
}
