package com.bacoder.parser.java.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.bacoder.parser.core.DumpVisitors;
import com.bacoder.parser.java.JavaLexer;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.adapter.CompilationUnitAdapter;
import com.bacoder.parser.java.api.CompilationUnit;

@Test
public class TestProgram1 extends JavaBaseTest {

  private CompilationUnit compilationUnit;

  @BeforeTest
  public void setup() throws IOException {
    String testName = getClass().getSimpleName();
    InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream(
            String.format("%s/%sCode.java", testName, testName));
    try {
      JavaLexer lexer = new JavaLexer(new ANTLRInputStream(inputStream));
      CommonTokenStream tokenStream = new CommonTokenStream(lexer);
      JavaParser parser = new JavaParser(tokenStream);
      compilationUnit = getAdapter(CompilationUnitAdapter.class).adapt(parser.compilationUnit());
    } finally {
      inputStream.close();
    }
  }

  public void testAST() throws IOException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    compilationUnit.visit(new DumpVisitors(outputStream));

    String ast = outputStream.toString("UTF-8");

    String testName = getClass().getSimpleName();
    InputStream inputStream =
        getClass().getClassLoader().getResourceAsStream(
            String.format("%s/%sAST.xml", testName, testName));
    Scanner scanner = new Scanner(inputStream, "UTF-8");
    try {
      String expected = scanner.useDelimiter("\\Z").next();
      Assert.assertEquals(ast.trim(), expected.trim());
    } finally {
      scanner.close();
    }
  }
}
