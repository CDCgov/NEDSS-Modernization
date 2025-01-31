/**
 * MessageTooLargeFaultMessage.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.co;

public class MessageTooLargeFaultMessage extends java.lang.Exception {
  private static final long serialVersionUID = 1447073656848L;
  private org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault faultMessage;

  public MessageTooLargeFaultMessage() {
    super("MessageTooLargeFaultMessage");
  }

  public MessageTooLargeFaultMessage(java.lang.String s) {
    super(s);
  }

  public MessageTooLargeFaultMessage(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public MessageTooLargeFaultMessage(java.lang.Throwable cause) {
    super(cause);
  }

  public void setFaultMessage(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.co.Client_ServiceStub.MessageTooLargeFault getFaultMessage() {
    return faultMessage;
  }
}
