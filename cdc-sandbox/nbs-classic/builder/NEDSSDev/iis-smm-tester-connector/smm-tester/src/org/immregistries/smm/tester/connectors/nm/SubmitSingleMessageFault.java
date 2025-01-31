
/**
 * SubmitSingleMessageFault.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.nm;

public class SubmitSingleMessageFault extends java.lang.Exception {

  private static final long serialVersionUID = 1352994966060L;

  private org.immregistries.smm.tester.connectors.tlep.Fault faultMessage;


  public SubmitSingleMessageFault() {
    super("SubmitSingleMessageFault");
  }

  public SubmitSingleMessageFault(java.lang.String s) {
    super(s);
  }

  public SubmitSingleMessageFault(java.lang.String s, java.lang.Throwable ex) {
    super(s, ex);
  }

  public SubmitSingleMessageFault(java.lang.Throwable cause) {
    super(cause);
  }


  public void setFaultMessage(org.immregistries.smm.tester.connectors.tlep.Fault msg) {
    faultMessage = msg;
  }

  public org.immregistries.smm.tester.connectors.tlep.Fault getFaultMessage() {
    return faultMessage;
  }
}
