package org.immregistries.smm.mover;

public class HL7 {

  public static final String ACK = "ACK";
  public static final String VXU = "VXU";

  public static final String AA = "AA";
  public static final String AE = "AE";
  public static final String AR = "AR";

  public static final String MSA = "MSA";
  public static final String ERR = "ERR";
  public static final String MSH = "MSH";
  public static final String BTS = "BTS";
  public static final String FTS = "FTS";
  public static final String BHS = "BHS";
  public static final String FHS = "FHS";

  public static boolean isFileBatchHeaderFooterSegment(String line) {
    return line.startsWith(HL7.FHS) || line.startsWith(HL7.BHS) || line.startsWith(HL7.FTS);
  }



  public static String readField(String line, int pos) {
    if (line == null) {
      return "";
    }
    if (line.startsWith("MSH") || line.startsWith("FHS") || line.startsWith("BHS")) {
      pos--;
    }
    if (pos < 1) {
      return "";
    }
    int posFirst = line.indexOf('|');
    while (posFirst != -1 && pos > 1) {
      posFirst++;
      posFirst = line.indexOf('|', posFirst);
      pos--;
    }
    if (posFirst == -1) {
      return "";
    }
    posFirst++;
    int posLast = line.indexOf('|', posFirst);
    if (posLast == -1) {
      posLast = line.length();
    }
    return line.substring(posFirst, posLast);
  }

}
