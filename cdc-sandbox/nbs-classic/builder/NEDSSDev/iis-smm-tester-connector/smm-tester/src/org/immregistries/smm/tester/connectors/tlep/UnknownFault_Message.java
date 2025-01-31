
/**
 * UnknownFault_Message.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.tlep;

public class UnknownFault_Message extends java.lang.Exception {

  private static final long serialVersionUID = 1352987427388L;

  private org.immregistries.smm.tester.connectors.tlep.Fault faultMessage;


  public UnknownFault_Message() {
    super("UnknownFault_Message");
  }

  public UnknownFault_Message(java.lang.String s) {
    super(s);
  }

  public UnknownFault_Message(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public UnknownFault_Message(java.lang.Throwable cause) {
    super(cause);
  }


  public void setFaultMessage(org.immregistries.smm.tester.connectors.tlep.Fault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.tlep.Fault getFaultMessage() {
    return faultMessage;
  }
}
