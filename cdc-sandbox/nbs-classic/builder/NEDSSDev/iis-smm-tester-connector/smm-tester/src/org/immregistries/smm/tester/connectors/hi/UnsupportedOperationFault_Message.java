
/**
 * UnsupportedOperationFault_Message.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.hi;

public class UnsupportedOperationFault_Message extends java.lang.Exception {

  private static final long serialVersionUID = 1357737547403L;

  private org.immregistries.smm.tester.connectors.hi.UnsupportedOperationFault faultMessage;


  public UnsupportedOperationFault_Message() {
    super("UnsupportedOperationFault_Message");
  }

  public UnsupportedOperationFault_Message(java.lang.String s) {
    super(s);
  }

  public UnsupportedOperationFault_Message(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public UnsupportedOperationFault_Message(java.lang.Throwable cause) {
    super(cause);
  }


  public void setFaultMessage(
      org.immregistries.smm.tester.connectors.hi.UnsupportedOperationFault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.hi.UnsupportedOperationFault getFaultMessage() {
    return faultMessage;
  }
}
