
/**
 * ASIISCallbackHandler.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.7.4 Built on : Oct 21, 2016
 * (10:47:34 BST)
 */

package org.immregistries.smm.tester.connectors.az.gov.azdhs.www;

/**
 * ASIISCallbackHandler Callback class, Users can extend this class and implement their own
 * receiveResult and receiveError methods.
 */
public abstract class ASIISCallbackHandler {



  protected Object clientData;

  /**
   * User can pass in any object that needs to be accessed once the NonBlocking Web service call is
   * finished and appropriate method of this CallBack is called.
   * 
   * @param clientData Object mechanism by which the user can pass in user data that will be
   *        avilable at the time this callback is called.
   */
  public ASIISCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }

  /**
   * Please use this constructor if you don't want to set any clientData
   */
  public ASIISCallbackHandler() {
    this.clientData = null;
  }

  /**
   * Get the client data
   */

  public Object getClientData() {
    return clientData;
  }


  /**
   * auto generated Axis2 call back method for sync_Submit method override this method for handling
   * normal response from sync_Submit operation
   */
  public void receiveResultsync_Submit(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * sync_Submit operation
   */
  public void receiveErrorsync_Submit(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for status_Query method override this method for handling
   * normal response from status_Query operation
   */
  public void receiveResultstatus_Query(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Status_QueryResponse result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * status_Query operation
   */
  public void receiveErrorstatus_Query(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for async_Submit method override this method for handling
   * normal response from async_Submit operation
   */
  public void receiveResultasync_Submit(
      org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Async_SubmitResponse result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * async_Submit operation
   */
  public void receiveErrorasync_Submit(java.lang.Exception e) {}



}
