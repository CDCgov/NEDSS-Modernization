

/**
 * Client_Service.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.hi;

/*
 * Client_Service java interface
 */

public interface Client_Service {


  /**
   * Auto generated method signature submit single message
   * 
   * @param submitSingleMessage0
   * 
   * @throws org.immregistries.smm.tester.connectors.hi.UnknownFault_Message :
   * @throws org.immregistries.smm.tester.connectors.hi.SecurityFault_Message :
   * @throws org.immregistries.smm.tester.connectors.hi.MessageTooLargeFault_Message :
   */


  public org.immregistries.smm.tester.connectors.hi.SubmitSingleMessageResponse submitSingleMessage(

      org.immregistries.smm.tester.connectors.hi.SubmitSingleMessage submitSingleMessage0)
      throws java.rmi.RemoteException

      , org.immregistries.smm.tester.connectors.hi.UnknownFault_Message,
      org.immregistries.smm.tester.connectors.hi.SecurityFault_Message,
      org.immregistries.smm.tester.connectors.hi.MessageTooLargeFault_Message;


  /**
   * Auto generated method signature for Asynchronous Invocations submit single message
   * 
   * @param submitSingleMessage0
   * 
   */
  public void startsubmitSingleMessage(

      org.immregistries.smm.tester.connectors.hi.SubmitSingleMessage submitSingleMessage0,

      final org.immregistries.smm.tester.connectors.hi.Client_ServiceCallbackHandler callback)

      throws java.rmi.RemoteException;



  /**
   * Auto generated method signature the connectivity test
   * 
   * @param connectivityTest2
   * 
   * @throws org.immregistries.smm.tester.connectors.hi.UnsupportedOperationFault_Message :
   * @throws org.immregistries.smm.tester.connectors.hi.UnknownFault_Message :
   */


  public org.immregistries.smm.tester.connectors.hi.ConnectivityTestResponse connectivityTest(

      org.immregistries.smm.tester.connectors.hi.ConnectivityTest connectivityTest2)
      throws java.rmi.RemoteException

      , org.immregistries.smm.tester.connectors.hi.UnsupportedOperationFault_Message,
      org.immregistries.smm.tester.connectors.hi.UnknownFault_Message;


  /**
   * Auto generated method signature for Asynchronous Invocations the connectivity test
   * 
   * @param connectivityTest2
   * 
   */
  public void startconnectivityTest(

      org.immregistries.smm.tester.connectors.hi.ConnectivityTest connectivityTest2,

      final org.immregistries.smm.tester.connectors.hi.Client_ServiceCallbackHandler callback)

      throws java.rmi.RemoteException;



  //
}
