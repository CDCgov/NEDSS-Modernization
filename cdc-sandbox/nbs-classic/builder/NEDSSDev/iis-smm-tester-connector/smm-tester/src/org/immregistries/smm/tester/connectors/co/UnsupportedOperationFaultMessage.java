/**
 * UnsupportedOperationFaultMessage.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.co;

public class UnsupportedOperationFaultMessage extends java.lang.Exception {
  private static final long serialVersionUID = 1447073656840L;
  private org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault faultMessage;

  public UnsupportedOperationFaultMessage() {
    super("UnsupportedOperationFaultMessage");
  }

  public UnsupportedOperationFaultMessage(java.lang.String s) {
    super(s);
  }

  public UnsupportedOperationFaultMessage(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public UnsupportedOperationFaultMessage(java.lang.Throwable cause) {
    super(cause);
  }

  public void setFaultMessage(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.co.Client_ServiceStub.UnsupportedOperationFault getFaultMessage() {
    return faultMessage;
  }
}
