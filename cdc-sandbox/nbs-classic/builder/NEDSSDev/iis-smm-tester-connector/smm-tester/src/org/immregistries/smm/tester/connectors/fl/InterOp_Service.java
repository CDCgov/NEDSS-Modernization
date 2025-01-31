

/**
 * InterOp_Service.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.fl;

/*
 * InterOp_Service java interface
 */

public interface InterOp_Service {


  /**
   * Auto generated method signature
   * 
   * @param connectivityTestFL0
   * 
   */


  public org.immregistries.smm.tester.connectors.fl.ConnectivityTestFLResponse connectivityTestFL(

      org.immregistries.smm.tester.connectors.fl.ConnectivityTestFL connectivityTestFL0)
      throws java.rmi.RemoteException;


  /**
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @param connectivityTestFL0
   * 
   */
  public void startconnectivityTestFL(

      org.immregistries.smm.tester.connectors.fl.ConnectivityTestFL connectivityTestFL0,

      final org.immregistries.smm.tester.connectors.fl.InterOp_ServiceCallbackHandler callback)

      throws java.rmi.RemoteException;



  /**
   * Auto generated method signature
   * 
   * @param submitSingleMessage2
   * 
   */


  public org.immregistries.smm.tester.connectors.fl.SubmitSingleMessageResponse submitSingleMessage(

      org.immregistries.smm.tester.connectors.fl.SubmitSingleMessage submitSingleMessage2)
      throws java.rmi.RemoteException;


  /**
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @param submitSingleMessage2
   * 
   */
  public void startsubmitSingleMessage(

      org.immregistries.smm.tester.connectors.fl.SubmitSingleMessage submitSingleMessage2,

      final org.immregistries.smm.tester.connectors.fl.InterOp_ServiceCallbackHandler callback)

      throws java.rmi.RemoteException;



  //
}
