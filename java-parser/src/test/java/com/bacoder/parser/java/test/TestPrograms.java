package com.bacoder.parser.java.test;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.apache.log4j.Logger;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.bacoder.parser.core.DumpVisitors;
import com.bacoder.parser.core.Pair;
import com.bacoder.parser.java.JavaLexer;
import com.bacoder.parser.java.JavaParser;
import com.bacoder.parser.java.adapter.CompilationUnitAdapter;
import com.bacoder.parser.java.api.CompilationUnit;

@Test
public class TestPrograms extends JavaBaseTest {

  private static final Logger LOG = Logger.getLogger(TestPrograms.class);

  public void test() throws IOException {
    List<Pair<File, File>> testCases = findTestCases();
    for (Pair<File, File> testCase : testCases) {
      LOG.info("Running test case " + testCase.getFirst().getParentFile().getName());
      CompilationUnit compilationUnit = loadProgram(testCase.getFirst());
      checkAST(compilationUnit, testCase.getSecond());
    }
  }

  private void checkAST(CompilationUnit compilationUnit, File xmlFile)
      throws FileNotFoundException, UnsupportedEncodingException {
    ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    compilationUnit.visit(new DumpVisitors(outputStream));
    String actual = outputStream.toString("UTF-8").trim();

    InputStream inputStream = new FileInputStream(xmlFile);
    Scanner scanner = new Scanner(inputStream, "UTF-8");
    String expected = "";
    try {
      expected = scanner.useDelimiter("\\Z").next().trim();
    } finally {
      scanner.close();
    }

    Assert.assertEquals(actual, expected);
  }

  private List<Pair<File, File>> findTestCases() {
    URL rootUrl = getClass().getClassLoader().getResource("TestPrograms");
    File root = new File(rootUrl.getFile());
    final List<Pair<File, File>> testCases = new ArrayList<Pair<File, File>>();
    root.listFiles(new FileFilter() {
      @Override
      public boolean accept(File file) {
        if (file.isDirectory()) {
          File javaFile = new File(file, file.getName() + ".java");
          File xmlFile = new File(file, file.getName() + ".xml");
          if (javaFile.canRead() && xmlFile.canRead()) {
            testCases.add(new Pair<File, File>(javaFile, xmlFile));
          }
        }
        return false;
      }
    });
    return testCases;
  }

  private CompilationUnit loadProgram(File programFile) throws IOException {
    InputStream inputStream = new FileInputStream(programFile);
    try {
      JavaLexer lexer = new JavaLexer(new ANTLRInputStream(inputStream));
      CommonTokenStream tokenStream = new CommonTokenStream(lexer);
      JavaParser parser = new JavaParser(tokenStream);
      return getAdapter(CompilationUnitAdapter.class).adapt(parser.compilationUnit());
    } finally {
      inputStream.close();
    }
  }
}
