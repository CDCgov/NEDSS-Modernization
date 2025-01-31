package org.immregistries.smm.tester.manager.query;

import java.io.BufferedReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class QueryConverterQBPZ34Z44 extends QueryConverter {

  public String convert(String message) {
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
      if (mshFields.size() > MAX_FIELDS_IN_SEGMENT) {
        messageId = System.currentTimeMillis() + messageId;
        sb.append("MSH|^~\\&|");
        sb.append(mshFields.get(3) + "|");
        sb.append(mshFields.get(4) + "|");
        sb.append(mshFields.get(5) + "|");
        sb.append(mshFields.get(6) + "|");
        sb.append(mshFields.get(7) + "|");
        sb.append(mshFields.get(8) + "|");
        sb.append("QBP^Q11^QBP_Q11|");
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
        sb.append("QPD|");
        sb.append("Z44^Request Evaluated Immunization History^CDCPHINVS|");
        sb.append(messageId + "|");
        sb.append(pidFields.get(3) + "|");
        sb.append(pidFields.get(5) + "|");
        sb.append(pidFields.get(6) + "|");
        sb.append(pidFields.get(7) + "|");
        sb.append(pidFields.get(8) + "|");
        sb.append(pidFields.get(11) + "|");
        sb.append(pidFields.get(13) + "|");
        sb.append(pidFields.get(24) + "|");
        sb.append(pidFields.get(25) + "|");
        sb.append(pidFields.get(33) + "|");
        sb.append(pidFields.get(34) + "|");
        sb.append('\r');
      }
      sb.append("RCP|I|20^RD&Records&HL70126|");
      sb.append('\r');

    } catch (Exception e) {
      sb.append("Unable to process: " + e.getMessage());
    }
    return sb.toString();
  }


}
