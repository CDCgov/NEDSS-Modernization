/**
 * Aliiashl7FaultException.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.al;

public class Aliiashl7FaultException extends java.lang.Exception {
  private static final long serialVersionUID = 1446748913964L;
  private org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault faultMessage;

  public Aliiashl7FaultException() {
    super("Aliiashl7FaultException");
  }

  public Aliiashl7FaultException(java.lang.String s) {
    super(s);
  }

  public Aliiashl7FaultException(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public Aliiashl7FaultException(java.lang.Throwable cause) {
    super(cause);
  }

  public void setFaultMessage(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Fault getFaultMessage() {
    return faultMessage;
  }
}
