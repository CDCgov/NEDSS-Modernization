package org.immregistries.smm.mover;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;

public class FileOut {
  private File file = null;
  private PrintWriter out = null;
  private boolean append = false;

  private void init() throws IOException {
    if (out == null) {
      out = new PrintWriter(new FileWriter(file, append));
    }
  }

  public FileOut(File file, boolean append) {
    this.file = file;
    this.append = append;
  }

  public boolean exists() {
    return file.exists();
  }


  public void print(String string) throws IOException {
    init();
    out.print(string);
  }

  public void printCommentLn(String string) throws IOException {
    init();
    out.print("--- ");
    out.print(string);
    out.print('\r');
  }

  public void printCommentLnMultiple(String s) {
    BufferedReader in = new BufferedReader(new StringReader(s));
    String line;
    try {
      while ((line = in.readLine()) != null) {
        printCommentLn(line);
      }
    } catch (IOException ioe) {
      ioe.printStackTrace();
    }
  }

  public void println() throws IOException {
    init();
    out.println();
  }

  public void println(String string) throws IOException {
    init();
    out.println(string);
  }

  public void print(Throwable t) throws IOException {
    init();
    t.printStackTrace(out);
  }

  public void close() {
    if (out != null) {
      out.close();
    }
  }
}
