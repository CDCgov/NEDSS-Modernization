package org.immregistries.smm.mover;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import org.immregistries.smm.tester.manager.HL7Reader;
//import org.immregistries.smm.transform.TestCaseMessage;

public class AckAnalyzer {

  public static enum ErrorType {
    UNKNOWN, AUTHENTICATION, SENDER_PROBLEM, RECEIVER_PROBLEM
  };

  public static enum AckType {
    DEFAULT, NMSIIS, ALERT, WEBIZ, MIIC, IRIS_IA, VIIS, NJSIIS, IRIS_ID, NESIIS, HP_WIR_DEFAULT;

    private boolean inHL7Format = true;
    protected String description = null;

    public String getDescription() {
      return description;
    }

    public boolean isInHL7Format() {
      return inHL7Format;
    }
  };

  static {
    AckType.NMSIIS.description =
        "The NMSIIS interface is first assumed to have a setup problem if any one of three conditions occurs: \n"
            + " + Message is shorter than 240 characters\n"
            + " + Message contains phrase |BAD MESSAGE|\n"
            + " + Message contains phrase FILE REJECTED\n\n"
            + "Transmission will stop if a setup problem is detected\n\n"
            + "A message is considered accepted if the following phrases are not found in the message: \n"
            + " + RECORD REJECTED\n" + " + MESSAGE REJECTED\n"
            + " + WARNING:  RXA #[n] IGNORED - REQUIRED FIELD RXA-\n\n";

    AckType.IRIS_ID.description =
        "The Idaho IRIS acknowledgement is considered accepted if it does not contain the phrase 'MESSAGE REJECTED'. \n\n"
            + "In addition special steps were taken to handle fact that some ACK messages are incorrectly labeled as VXU messages. "
            + "In the case where the ACK message is labeled as a VXU the responses is assumed to be an acknowledgement and read as such. \n";

    AckType.ALERT.description =
        "Follows the CDC/AIRA standard except if the message contains the phrase \"Record Rejected\" or \"RXA #[n] IGNORED\" "
            + "then this is an error even if it's not a E. ";

    AckType.ALERT.description =
        "If MSA-1 is AR then the message was rejected, otherwise it was accepted.";

    AckType.HP_WIR_DEFAULT.description =
        "If the phrase \"REJECTED\" or \"PID #1 IGNORED\" or \"RXA #[n] IGNORED\" "
            + "is found in the response message then the message is considered to not be completely accepted. ";
  }

  public static HL7Reader getMessageReader(String ackMessageText, AckType ackType) {
    if (ackMessageText == null || ackMessageText.length() == 0 || !ackType.inHL7Format) {
      return null;
    }
    HL7Reader ackMessageReader = new HL7Reader(ackMessageText);
    if (!ackMessageReader.advanceToSegment("MSH")) {
      return null;
    }
    String type = ackMessageReader.getValue(9);
    if (ackType == AckType.IRIS_ID) {
      if (!type.equals("VXU") && !type.equals("ACK")) {
        return null;
      }
    } else {
      if (!type.equals("ACK")) {
        return null;
      }
    }
    return ackMessageReader;
  }

  private ErrorType errorType = null;
  private boolean ackMessage = false;
  private boolean positive = false;
  private boolean temporarilyUnavailable = false;
  private boolean setupProblem = false;
  private String setupProblemDescription = "";
  private List<String> segments;
  private FileOut errorFileOut = null;
  //private TestCaseMessage testCaseMessage = null;

  private void log(String s) {
    if (errorFileOut != null) {
      try {
        errorFileOut.printCommentLn(s);
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
    /*if (testCaseMessage != null) {
      testCaseMessage.log(s);
    }*/
  }

  public String getSetupProblemDescription() {
    return setupProblemDescription;
  }

  public void setSetupProblemDescription(String setupProblemDescription) {
    this.setupProblemDescription = setupProblemDescription;
  }

  public boolean isTemporarilyUnavailable() {
    return temporarilyUnavailable;
  }

  public void setTemporarilyUnavailable(boolean temporarilyUnavailable) {
    this.temporarilyUnavailable = temporarilyUnavailable;
  }

  public boolean hasSetupProblem() {
    return setupProblem;
  }

  public void setSetupProblem(boolean setupProblem) {
    this.setupProblem = setupProblem;
  }

  public ErrorType getErrorType() {
    return errorType;
  }

  public void setErrorType(ErrorType errorType) {
    this.errorType = errorType;
  }

  private String ackCode = "";

  public boolean isAckMessage() {
    return ackMessage;
  }

  public boolean isPositive() {
    return positive;
  }

  public void setPositive(boolean positive) {
    this.positive = positive;
  }

  public String getAckCode() {
    return ackCode;
  }

  public void setAckCode(String ackCode) {
    this.ackCode = ackCode;
  }

  public AckAnalyzer(String ack) {
    this(ack, AckType.DEFAULT, null);
  }

  public AckAnalyzer(String ack, AckType ackType) {
    this(ack, ackType, null);
  }

  /*public AckAnalyzer(String ack, AckType ackType, FileOut errorFileOut) {
    this(ack, ackType, errorFileOut);
  }*/

  public AckAnalyzer(String ack, AckType ackType, FileOut errorFileOut) {
    while (ack != null && ack.length() > 0 && ack.charAt(0) <= ' ') {
      ack = ack.substring(1);
    }
    this.errorFileOut = errorFileOut;
    //this.testCaseMessage = testCaseMessage;
    log("  Ack Type = " + ackType);

    ack = convertToSegments(ack);
    String ackUpperCase = ack.toUpperCase();

    boolean isNotAck = false;
    if (ack.length() == 0) {
      isNotAck = true;
      log("Returned result is not an acknowledgement message: no acknowledgement message returned");
    } else if (!ack.startsWith("MSH|")) {
      isNotAck = true;
      log("Returned result is not an acknowledgement message: first line does not start with MSH|");
    } else if (!getFieldValue("MSH", 9).equals("ACK")) {
      isNotAck = true;
      log("Returned result is not an acknowledgement message: MSH-9 is not ACK, it is '"
          + getFieldValue("MSH", 9) + "'");
    }
    if (isNotAck) {
      if (ackType.equals(AckType.IRIS_ID)) {
        isNotAck = false;
        ackMessage = true;
        int messageRejectedPos = ackUpperCase.indexOf("MESSAGE REJECTED");
        positive = ackUpperCase.startsWith("MSH|^~\\&|") && messageRejectedPos == -1;
        if (!positive) {
          log("The phrase \"MESSAGE REJECTED\" appeared in the message so major issue must have happened");
        }
        ackCode = positive ? "AA" : "AE";
      } else if (ackType.equals(AckType.NJSIIS)) {
        isNotAck = false;
        ackMessage = true;
        positive = ackUpperCase.equals("SUCCESS");
        if (positive) {
          log("The phrase \"SUCCESS\" appeared in the message so the message assumed to have been accepted");
        } else {
          log("The phrase \"SUCCESS\" did NOT appear in the message so the message assumed to NOT  have been accepted");
        }
        ackCode = positive ? "AA" : "AE";
      } else {
        ackMessage = false;
        positive = false;
        setupProblem = true;
        if (ack != null && ackType == AckType.NMSIIS) {
          if (ack.length() < 240) {
            setupProblemDescription = ack;
          } else {
            setupProblemDescription = "Unexpected non-HL7 response, please verify connection URL";
          }
        } else {
          setupProblemDescription = "Unexpected non-HL7 response, please verify connection URL";
        }
      }
    } else {
      ackMessage = true;
      ackCode = getFieldValue("MSA", 1);

      boolean alertProblem = false;
      if (ackType.equals(AckType.ALERT)) {
        int warningRxaPos1 = ackUpperCase.indexOf("|RXA #");
        int warningRxaPos2 = -1;
        if (warningRxaPos1 > -1) {
          warningRxaPos2 = ackUpperCase.indexOf(" IGNORED");
          alertProblem = warningRxaPos2 != -1;
          if (alertProblem) {
            log("Found \"RXA#\" and \" IGNORED\" in the ack message. Assuming at least one vaccination was not accepted. ");
          }
        } else {
          if (ackUpperCase.indexOf("RECORD REJECTED") != -1) {
            alertProblem = true;
            log("Found \"RECORD REJECTED\" in message. Assuming not accepted.");
          }
        }
      }

      if (ackType.equals(AckType.NMSIIS)) {
        setupProblem = ackUpperCase.indexOf("|BAD MESSAGE|") != -1
            || ackUpperCase.indexOf("FILE REJECTED") != -1;
        if (setupProblem) {
          log("Setup problem found, message contains phrase |BAD MESSAGE| or File Rejected.");
        }
        int recordRejectedPos = ackUpperCase.indexOf("RECORD REJECTED");
        int messageRejectedPos = ackUpperCase.indexOf("MESSAGE REJECTED");
        int warningRxaPos1 = ackUpperCase.indexOf("WARNING:  RXA #");
        int warningRxaPos2 = -1;
        if (warningRxaPos1 > -1) {
          warningRxaPos2 = ackUpperCase.indexOf(" IGNORED - REQUIRED FIELD RXA-");
        }
        boolean recordNotRejected =
            recordRejectedPos == -1 && messageRejectedPos == -1 && warningRxaPos2 == -1;
        if (!recordNotRejected) {
          log("Record was rejected, message contains phrase Record Rejected");
        }
        positive = !setupProblem && recordNotRejected;
      } else if (ackType.equals(AckType.MIIC)) {
        int recordRejectedPos = ackUpperCase.indexOf("REJECTED");
        int pidRejectedPos = ackUpperCase.indexOf("PID #1 IGNORED");
        boolean arMSA = getFieldValue("MSA", 1).equals("AR");
        positive = ackUpperCase.startsWith("MSH|^~\\&|") && recordRejectedPos == -1
            && pidRejectedPos == -1 && !arMSA;
        if (positive) {
          int rxaRejectedPos = ackUpperCase.indexOf("RXA #");
          if (rxaRejectedPos != -1) {
            rxaRejectedPos = ackUpperCase.indexOf(" ", rxaRejectedPos + 5);
            if (rxaRejectedPos != -1) {
              positive = !ackUpperCase.substring(rxaRejectedPos + +1).startsWith("IGNORED");
            }
          }
        }
        if (!positive) {
          log("The word rejected appeared in the message so the message was rejected");
        }
      } else if (ackType.equals(AckType.HP_WIR_DEFAULT)) {
        int recordRejectedPos = ackUpperCase.indexOf("REJECTED");
        int pidRejectedPos = ackUpperCase.indexOf("PID #1 IGNORED");
        boolean arMSA = getFieldValue("MSA", 1).equals("AR");
        positive = ackUpperCase.startsWith("MSH|^~\\&|") && recordRejectedPos == -1
            && pidRejectedPos == -1 && !arMSA;
        if (positive) {
          int rxaRejectedPos = ackUpperCase.indexOf("RXA #");
          if (rxaRejectedPos != -1) {
            rxaRejectedPos = ackUpperCase.indexOf(" ", rxaRejectedPos + 5);
            if (rxaRejectedPos != -1) {
              positive = !ackUpperCase.substring(rxaRejectedPos + +1).startsWith("IGNORED");
            }
          }
        }
        if (!positive) {
          log("The word rejected appeared in the message so the message was rejected");
        }
      } else if (ackType.equals(AckType.VIIS)) {
        String[] rejectPhrases = {"Unsupported HL7 version or trigger".toUpperCase(), "REJECTED",
            "PID #1 IGNORED", "BAD MESSAGE"};

        positive = ackUpperCase.startsWith("MSH|^~\\&|");
        for (String rejectPhrase : rejectPhrases) {
          int pos = ackUpperCase.indexOf(rejectPhrase);
          if (pos > 0) {
            positive = false;
            break;
          }
        }
        if (positive) {
          int rxaRejectedPos = ackUpperCase.indexOf("RXA #");
          if (rxaRejectedPos != -1) {
            rxaRejectedPos = ackUpperCase.indexOf(" ", rxaRejectedPos + 5);
            if (rxaRejectedPos != -1) {
              positive = !ackUpperCase.substring(rxaRejectedPos + +1).startsWith("IGNORED");
            }
          }
        }
        if (!positive) {
          log("The word rejected appeared in the message so the message was rejected");
        }
      } else if (ackType.equals(AckType.IRIS_IA)) {
        // IA defines 5 levels of errors: None, Low, Moderate, High, and
        // Critical
        int recordRejectedPos = ackUpperCase.indexOf("REJECTED");
        int pidRejectedPos = ackUpperCase.indexOf("PID #1 IGNORED");
        positive = ackUpperCase.startsWith("MSH|^~\\&|") && recordRejectedPos == -1
            && pidRejectedPos == -1;
        if (positive) {
          int rxaRejectedPos = ackUpperCase.indexOf("RXA #");
          if (rxaRejectedPos != -1) {
            rxaRejectedPos = ackUpperCase.indexOf(" ", rxaRejectedPos + 5);
            if (rxaRejectedPos != -1) {
              positive = !ackUpperCase.substring(rxaRejectedPos + +1).startsWith("IGNORED");
            }
          }
        }
        if (!positive) {
          log("The word rejected appeared in the message so the message was rejected");
        }
      } else if (ackType.equals(AckType.IRIS_ID)) {
        //
        int messageRejectedPos = ackUpperCase.indexOf("MESSAGE REJECTED");
        positive = ackUpperCase.startsWith("MSH|^~\\&|") && messageRejectedPos == -1;
        if (!positive) {
          log("The phrase \"MESSAGE REJECTED\" appeared in the message so major issue must have happened");
        }
      } else if (ackType.equals(AckType.ALERT) && alertProblem) {
        positive = false;
        log("The phrase in response indicates important information was not accepted");
      } else if (ackType.equals(AckType.WEBIZ)) {
        if (ackCode.equals("AA")) {
          positive = true;
        } else if (ackCode.equals("AE")) {
          positive = true;
          if (ack.indexOf("Not processing order group") != -1) {
            positive = false;
          } else if (ack.indexOf("Application internal error") != -1) {
            positive = false;
          }
        } else {
          positive = false;
        }
      } else if (ackType.equals(AckType.NESIIS)) {
        if (ackCode.equals("AA") || ackCode.equals("AE")) {
          positive = true;
        } else if (ackCode.equals("AR")) {
          positive = false;
        }
      } else {
        log("Using default rules to determine if accepted");
        if (ackCode.equals("AA")) {
          positive = true;
          log("Ack code is AA, so message must have been accepted.");
        } else if (ackCode.equals("AR")) {
          positive = false;
          log("Ack code is AR, so message could not have been accepted");
        } else {
          log("Ack code is AE, so message may or may not have been accepted");
          positive = true;
          boolean noSeverity = true;
          int pos = 1;
          String[] values = null;
          while ((values = getFieldValues("ERR", pos, 4)) != null) {
            if (values.length > 0) {
              if (!values[0].equals("")) {
                noSeverity = false;
              }
              if (values[0].equals("E")) {
                log("Found at least one ERR segment with ERR-4 = 'E'. This message was not accepted.");
                positive = false;
                break;
              }
            }
            pos++;
          }
          if (noSeverity) {
            log("ERR-4 was never filled in, or there are no ERR segments (both of which should not happen) so assuming message was not accepted. ");
            positive = false;
          }
        }

      }
    }
  }

  private String convertToSegments(String ack) {
    StringBuilder ackActual = new StringBuilder();
    segments = new ArrayList<String>();
    if (ack != null) {
      BufferedReader in = new BufferedReader(new StringReader(ack));
      String line;
      try {
        while ((line = in.readLine()) != null) {
          if (line.startsWith("FHS") || line.startsWith("BHS") || line.startsWith("BTS")
              || line.startsWith("FTS")) {
            // ignore these lines
          } else {
            segments.add(line);
            ackActual.append(line);
            ackActual.append("\r");
          }
        }
      } catch (IOException ioe) {
        ioe.printStackTrace();
      }
    }
    return ackActual.toString();
  }

  private String getFieldValue(String segmentName, int pos) {
    String[] values = getFieldValues(segmentName, 1, pos);
    if (values != null && values.length > 0 && values[0] != null) {
      return values[0];
    }
    return "";
  }

  private String[] getFieldValues(String segmentName, int segmentCount, int pos) {
    if (!segmentName.equals("MSH")) {
      pos++;
    }
    for (String segment : segments) {
      if (segment.startsWith(segmentName + "|")) {
        segmentCount--;
        if (segmentCount == 0) {
          int startPos = -1;
          int endPos = -1;
          while (pos > 0) {
            pos--;
            if (endPos < segment.length()) {
              startPos = endPos + 1;
              endPos = segment.indexOf("|", startPos);
              if (endPos == -1) {
                endPos = segment.length();
              }
            }
          }
          String value = segment.substring(startPos, endPos);
          int repeatPos = value.indexOf("~");
          if (repeatPos != -1) {
            value = value.substring(0, repeatPos);
          }
          return value.split("\\^");
        }
      }
    }

    return null;
  }

}
