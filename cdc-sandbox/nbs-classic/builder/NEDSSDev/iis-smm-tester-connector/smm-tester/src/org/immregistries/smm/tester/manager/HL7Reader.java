package org.immregistries.smm.tester.manager;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class HL7Reader {

  private List<String> originalLineList = null;
  private List<List<String>> segmentList = null;
  private List<String> fieldList = null;

  private int segmentPosition = -1;
  private String fieldSeparator = null;

  public String getFieldSeparator() {
    return fieldSeparator;
  }

  public HL7Reader(String message) {
    if (message != null) {
      try {
        originalLineList = new ArrayList<String>();
        segmentList = new ArrayList<List<String>>();
        BufferedReader in = new BufferedReader(new StringReader(message));
        String line;
        while ((line = in.readLine()) != null) {
          line = line.trim();
          if (fieldSeparator == null && line.length() > 3) {
            fieldSeparator = line.substring(3, 4);
          }
          if (line.length() > 3 && line.charAt(3) == '|') {
            fieldList = new ArrayList<String>();
            int startPos = 0;
            while (startPos >= 0) {
              int endPos = line.indexOf('|', startPos);
              if (endPos == -1) {
                endPos = line.length();
              }
              fieldList.add(line.substring(startPos, endPos));
              if (startPos == 0 && (line.startsWith("MSH|")
                  || line.startsWith("BHS|") | line.startsWith("FHS|"))) {
                fieldList.add("|");
              }
              startPos = endPos + 1;
              if (startPos >= line.length()) {
                startPos = -1;
              }
            }
            originalLineList.add(line);
            segmentList.add(fieldList);
          }
        }
      } catch (IOException ioe) {
        // shouldn't get IOException when reading string
        throw new RuntimeException("Unexpected ioe exception when reading string", ioe);
      }
      if (segmentList.size() > 0) {
        fieldList = segmentList.get(0);
      }
    }
  }

  public int getSegmentCount() {
    return segmentList != null ? segmentList.size() : 0;
  }

  public String getSegmentName() {
    if (fieldList != null && fieldList.size() > 0) {
      return fieldList.get(0);
    }
    return "";
  }

  public boolean advance() {
    if (segmentList != null && segmentPosition < segmentList.size()) {
      segmentPosition++;
      if (segmentPosition < segmentList.size()) {
        fieldList = segmentList.get(segmentPosition);
        return true;
      }
    }
    fieldList = null;
    return false;
  }

  public boolean advanceToSegment(String segmentName) {
    if (segmentList != null && segmentPosition < segmentList.size()) {
      segmentPosition++;
      while (segmentPosition < segmentList.size()) {
        fieldList = segmentList.get(segmentPosition);
        if (fieldList.get(0).equals(segmentName)) {
          return true;
        }
        segmentPosition++;
      }
    }
    fieldList = null;
    return false;
  }

  public boolean advanceToSegmentWithValue(String segmentName, int fieldNum, String value) {
    if (segmentList != null && segmentPosition < segmentList.size()) {
      segmentPosition++;
      while (segmentPosition < segmentList.size()) {
        fieldList = segmentList.get(segmentPosition);
        if (fieldList.get(0).equals(segmentName)) {
          if (value.equalsIgnoreCase(getValue(fieldNum))) {
            return true;
          }
        }
        segmentPosition++;
      }
    }
    fieldList = null;
    return false;
  }

  public boolean advanceToSegment(String segmentName, String beforeSegmentName) {
    if (segmentList != null && segmentPosition < segmentList.size()) {
      int peakAhead = segmentPosition + 1;
      if (peakAhead < segmentList.size()) {
        if (segmentList.get(peakAhead).get(0).equals(beforeSegmentName)) {
          fieldList = null;
          return false;
        }
      }
      segmentPosition++;
      while (segmentPosition < segmentList.size()) {
        fieldList = segmentList.get(segmentPosition);
        if (fieldList.get(0).equals(segmentName)) {
          return true;
        }
        peakAhead = segmentPosition + 1;
        if (peakAhead < segmentList.size()) {
          if (segmentList.get(peakAhead).get(0).equals(beforeSegmentName)) {
            fieldList = null;
            return false;
          }
        }
        segmentPosition++;
      }
    }
    fieldList = null;
    return false;
  }

  public int getSegmentPosition() {
    return segmentPosition;
  }

  public void gotoSegmentPosition(int segmentPosition) {
    this.segmentPosition = segmentPosition;
  }

  public boolean advanceToSegment(String segmentName, String[] beforeSegmentNames) {
    if (segmentList != null && segmentPosition < segmentList.size()) {
      int peakAhead = segmentPosition + 1;
      if (peakAhead < segmentList.size()) {
        for (String beforeSegmentName : beforeSegmentNames) {
          if (segmentList.get(peakAhead).get(0).equals(beforeSegmentName)) {
            fieldList = null;
            return false;
          }
        }
      }
      segmentPosition++;
      while (segmentPosition < segmentList.size()) {
        fieldList = segmentList.get(segmentPosition);
        if (fieldList.get(0).equals(segmentName)) {
          return true;
        }
        peakAhead = segmentPosition + 1;
        if (peakAhead < segmentList.size()) {
          for (String beforeSegmentName : beforeSegmentNames) {
            if (segmentList.get(peakAhead).get(0).equals(beforeSegmentName)) {
              fieldList = null;
              return false;
            }
          }
        }
        segmentPosition++;
      }
    }
    fieldList = null;
    return false;
  }

  public String getOriginalField(int fieldNum) {
    if (segmentList != null) {
      if (fieldList != null && fieldNum < fieldList.size()) {
        return fieldList.get(fieldNum);
      }
    }
    return "";
  }

  public String getOriginalSegment() {
    if (originalLineList != null) {
      if (segmentPosition >= 0 && segmentPosition < originalLineList.size()) {
        return originalLineList.get(segmentPosition);
      }
    }
    return "";
  }

  public String getValue(int fieldNum) {
    String field = getOriginalField(fieldNum);
    if (fieldNum <= 2) {
      String segmentName = getSegmentName();
      if (segmentName.equals("MSH") || segmentName.equals("BHS") || segmentName.equals("FHS")) {
        return field;
      }
    }
    field = cutoff(field, "~");
    field = cutoff(field, "^");
    field = cutoff(field, "&");
    return field;
  }

  public String getValue(int fieldNum, int componentNum) {
    String field = getOriginalField(fieldNum);
    return getValueInternal(componentNum, field);
  }

  public String getValue(int fieldNum, int componentNum, int subcomponentNum) {
    String field = getOriginalField(fieldNum);
    return getValueInternal(componentNum, field, subcomponentNum);
  }

  private String getValueInternal(int componentNum, String field) {
    int i = 1;
    while (i < componentNum) {
      int pos = field.indexOf("^");
      if (pos == -1 || (pos + 1) == field.length()) {
        return "";
      }
      field = field.substring(pos + 1);
      i++;
    }
    field = cutoff(field, "~");
    field = cutoff(field, "^");
    field = cutoff(field, "&");
    return field;
  }

  private String getValueInternal(int componentNum, String field, int subcomponentNum) {
    int i = 1;
    while (i < componentNum) {
      int pos = field.indexOf("^");
      if (pos == -1 || (pos + 1) == field.length()) {
        return "";
      }
      field = field.substring(pos + 1);
      i++;
    }
    field = cutoff(field, "~");
    field = cutoff(field, "^");
    i = 1;
    while (i < subcomponentNum) {
      int pos = field.indexOf("&");
      if (pos == -1 || (pos + 1) == field.length()) {
        return "";
      }
      field = field.substring(pos + 1);
      i++;
    }
    field = cutoff(field, "&");
    return field;
  }

  public int getRepeatCount(int fieldNum) {
    String field = getOriginalField(fieldNum);
    int count = 1;
    for (char c : field.toCharArray()) {
      if (c == '~') {
        count++;
      }
    }
    return count;
  }

  public String getValueBySearchingRepeats(int fieldNum, int componentNum, String searchValue,
      int searchComponentNum) {
    String field = getOriginalField(fieldNum);
    int pos = 0;
    while (pos < field.length()) {
      int endPos = field.indexOf("~");
      if (endPos == -1) {
        endPos = field.length();
      }
      String fieldRep = field.substring(pos, endPos);

      boolean found = getValueInternal(searchComponentNum, fieldRep).equals(searchValue);

      if (found) {
        return getValueInternal(componentNum, fieldRep);
      }

      pos = endPos + 1;

      if (pos < field.length()) {
        field = field.substring(pos);
        pos = 0;
      } else {
        return "";
      }
    }

    return "";
  }

  public String getValueRepeat(int fieldNum, int componentNum, int repeatNum) {
    String field = getOriginalField(fieldNum);
    int i = 1;
    while (i < repeatNum) {
      int pos = field.indexOf("~");
      if (pos == -1 || (pos + 1) == field.length()) {
        return "";
      }
      field = field.substring(pos + 1);
      i++;
    }
    field = cutoff(field, "~");
    i = 1;
    while (i < componentNum) {
      int pos = field.indexOf("^");
      if (pos == -1 || (pos + 1) == field.length()) {
        return "";
      }
      field = field.substring(pos + 1);
      i++;
    }
    field = cutoff(field, "^");
    field = cutoff(field, "&");
    return field;

  }

  private String cutoff(String field, String cutoffAt) {
    int pos = field.indexOf(cutoffAt);
    if (pos != -1) {
      return field.substring(0, pos);
    }
    return field;
  }

  public void resetPostion() {
    segmentPosition = -1;
  }
}
