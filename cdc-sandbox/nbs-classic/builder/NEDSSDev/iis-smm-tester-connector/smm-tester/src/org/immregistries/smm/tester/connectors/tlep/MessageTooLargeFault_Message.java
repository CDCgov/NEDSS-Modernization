
/**
 * MessageTooLargeFault_Message.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.tlep;

public class MessageTooLargeFault_Message extends java.lang.Exception {

  private static final long serialVersionUID = 1352987427399L;

  private org.immregistries.smm.tester.connectors.tlep.MessageTooLargeFault faultMessage;


  public MessageTooLargeFault_Message() {
    super("MessageTooLargeFault_Message");
  }

  public MessageTooLargeFault_Message(java.lang.String s) {
    super(s);
  }

  public MessageTooLargeFault_Message(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public MessageTooLargeFault_Message(java.lang.Throwable cause) {
    super(cause);
  }


  public void setFaultMessage(
      org.immregistries.smm.tester.connectors.tlep.MessageTooLargeFault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.tlep.MessageTooLargeFault getFaultMessage() {
    return faultMessage;
  }
}
