package org.immregistries.smm.tester.manager.query;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public  class QueryConverterVXQ extends QueryConverter{
  
  public  String convert(String message)
  {
    StringBuilder sb = new StringBuilder();
    List<String> mshFields = new ArrayList<String>();
    List<String> pidFields = new ArrayList<String>();
    try {
      BufferedReader reader = new BufferedReader(new StringReader(message));
      String line;
      while ((line = reader.readLine()) != null) {
        if (line.startsWith("MSH|")) {
          mshFields.add(""); // pad so that result matches specification
          readFields(mshFields, line);
        } else if (line.startsWith("PID|")) {
          readFields(pidFields, line);
        }

      }
      String messageId = "." + getCount();
      String queryDateTime = "";
      if (mshFields.size() > MAX_FIELDS_IN_SEGMENT) {
        messageId = System.currentTimeMillis() + messageId;
        sb.append("MSH|^~\\&|");
        sb.append(mshFields.get(3) + "|");
        sb.append(mshFields.get(4) + "|");
        sb.append(mshFields.get(5) + "|");
        sb.append(mshFields.get(6) + "|");
        sb.append(mshFields.get(7) + "|");
        queryDateTime = mshFields.get(7);
        sb.append(mshFields.get(8) + "|");
        sb.append("VXQ^V01|");
        sb.append(messageId + "|");
        sb.append(mshFields.get(11) + "|");
        sb.append(mshFields.get(12) + "|");
        sb.append(mshFields.get(13) + "|");
        sb.append(mshFields.get(14) + "|");
        sb.append("NE|");
        sb.append("AL|");
        sb.append(mshFields.get(17) + "|");
        sb.append(mshFields.get(18) + "|");
        sb.append(mshFields.get(19) + "|");
        sb.append(mshFields.get(20) + "|");
        sb.append("Z34^CDCPHINVS|");
        sb.append('\r');
      }
      if (pidFields.size() == MAX_FIELDS_IN_SEGMENT) {
        String pid3[] = pidFields.get(3).split("\\~");
        String ssn = "";
        String mrn = "";
        for (String f : pid3) {
          if (f != null && f.length() > 0) {
            if (f.indexOf("^SS") != -1) {
              int pos = f.indexOf("^");
              ssn = f.substring(0, pos);
            } else if (f.indexOf("^MR") != -1) {
              int pos = f.indexOf("^");
              mrn = f.substring(0, pos);
            }

          }
        }

        sb.append("QRD|");
        sb.append(queryDateTime + "|");
        sb.append("R|");
        sb.append("I|");
        sb.append(messageId + "|");
        sb.append("|");
        sb.append("|");
        sb.append("20^RD|");
        String[] name = pidFields.get(5).split("\\^");
        sb.append(mrn + "^");
        if (name.length > 0) {
          if (name[0] != null) {
            sb.append(name[0]);
          }
          sb.append("^");
          if (name.length > 1) {
            if (name[1] != null) {
              sb.append(name[1]);
            }
            sb.append("^");
            if (name.length > 2) {
              if (name[2] != null) {
                sb.append(name[2]);
              }
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("^");
              sb.append("MR");
            }
          }
        }
        sb.append("|");
        sb.append("VXI^Vaccine Information^HL70048|\r");

        sb.append("QRF|");
        sb.append("NV0000|");
        sb.append("|");
        sb.append("|");
        sb.append("|");
        sb.append(ssn + "~"); // 1 SSN
        sb.append(pidFields.get(7) + "~"); // 2 DOB
        sb.append("~"); // 3 Birth State
        sb.append("~"); // 4 Medicare #
        sb.append("~"); // 5 Medicaid #
        sb.append("~"); // 6 Mother's name
        sb.append(pidFields.get(6) + "~"); // 7 Mother's maiden name
        sb.append("~"); // 8 Patient state registry id
        sb.append("~"); // 9 Father's name
        sb.append(mrn); // 10 Local patient identifier
        sb.append("|");
        sb.append('\r');
      }

      sb.append('\r');

    } catch (Exception e) {
      sb.append("Unable to process: " + e.getMessage());
    }
    return sb.toString();
  }

}
