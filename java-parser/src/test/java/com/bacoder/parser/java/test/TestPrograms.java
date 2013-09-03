package com.bacoder.parser.java.test;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.testng.annotations.Test;

import com.bacoder.parser.core.Pair;
import com.bacoder.parser.java.api.CompilationUnit;

@Test
public class TestPrograms extends JavaBaseTest {

  private static final Logger LOG = Logger.getLogger(TestPrograms.class);

  public void test() throws IOException {
    List<Pair<File, File>> testCases = findTestCases("TestPrograms", "java");
    for (Pair<File, File> testCase : testCases) {
      LOG.info("Running test case " + testCase.getFirst().getParentFile().getName());
      CompilationUnit compilationUnit = getProgram(testCase.getFirst());
      verifyAST(compilationUnit, testCase.getSecond());
    }
  }
}
