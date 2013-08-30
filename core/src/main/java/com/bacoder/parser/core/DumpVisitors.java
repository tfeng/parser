package com.bacoder.parser.core;

import java.io.OutputStream;

public class DumpVisitors extends Visitors {

  public DumpVisitors(OutputStream outputStream) {
    super(Node.class, new DumpVisitor(outputStream));
  }

  public DumpVisitors(OutputStream outputStream, String indent) {
    super(Node.class, new DumpVisitor(outputStream, indent));
  }
}
