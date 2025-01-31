/**
 * SecurityFaultMessage.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.co;

public class SecurityFaultMessage extends java.lang.Exception {
  private static final long serialVersionUID = 1447073656864L;
  private org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault faultMessage;

  public SecurityFaultMessage() {
    super("SecurityFaultMessage");
  }

  public SecurityFaultMessage(java.lang.String s) {
    super(s);
  }

  public SecurityFaultMessage(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public SecurityFaultMessage(java.lang.Throwable cause) {
    super(cause);
  }

  public void setFaultMessage(
      org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.co.Client_ServiceStub.SecurityFault getFaultMessage() {
    return faultMessage;
  }
}
