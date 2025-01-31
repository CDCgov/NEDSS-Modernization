

/**
 * ASIIS.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.7.4 Built on : Oct 21, 2016
 * (10:47:34 BST)
 */

package org.immregistries.smm.tester.connectors.az.gov.azdhs.www;

/*
 * ASIIS java interface
 */

public interface ASIIS {


  /**
   * Auto generated method signature
   * 
   * @param sync_Submit0
   * 
   */


  public org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse sync_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit sync_Submit0)
      throws java.rmi.RemoteException;


  /**
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @param sync_Submit0
   * 
   */
  public void startsync_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit sync_Submit0,

      final org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISCallbackHandler callback)

      throws java.rmi.RemoteException;



  /**
   * Auto generated method signature
   * 
   * @param status_Query2
   * 
   */


  public org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse status_Query(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query status_Query2)
      throws java.rmi.RemoteException;


  /**
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @param status_Query2
   * 
   */
  public void startstatus_Query(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_Query status_Query2,

      final org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISCallbackHandler callback)

      throws java.rmi.RemoteException;



  /**
   * Auto generated method signature
   * 
   * @param async_Submit4
   * 
   */


  public org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse async_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit async_Submit4)
      throws java.rmi.RemoteException;


  /**
   * Auto generated method signature for Asynchronous Invocations
   * 
   * @param async_Submit4
   * 
   */
  public void startasync_Submit(

      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_Submit async_Submit4,

      final org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISCallbackHandler callback)

      throws java.rmi.RemoteException;



  //
}
