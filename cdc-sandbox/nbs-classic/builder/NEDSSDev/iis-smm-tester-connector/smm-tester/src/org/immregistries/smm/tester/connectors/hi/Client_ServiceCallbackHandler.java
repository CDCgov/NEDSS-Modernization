
/**
 * Client_ServiceCallbackHandler.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.2 Built on : Apr 17, 2012
 * (05:33:49 IST)
 */

package org.immregistries.smm.tester.connectors.hi;

/**
 * Client_ServiceCallbackHandler Callback class, Users can extend this class and implement their own
 * receiveResult and receiveError methods.
 */
public abstract class Client_ServiceCallbackHandler {



  protected Object clientData;

  /**
   * User can pass in any object that needs to be accessed once the NonBlocking Web service call is
   * finished and appropriate method of this CallBack is called.
   * 
   * @param clientData Object mechanism by which the user can pass in user data that will be
   *        avilable at the time this callback is called.
   */
  public Client_ServiceCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }

  /**
   * Please use this constructor if you don't want to set any clientData
   */
  public Client_ServiceCallbackHandler() {
    this.clientData = null;
  }

  /**
   * Get the client data
   */

  public Object getClientData() {
    return clientData;
  }


  /**
   * auto generated Axis2 call back method for submitSingleMessage method override this method for
   * handling normal response from submitSingleMessage operation
   */
  public void receiveResultsubmitSingleMessage(
      org.immregistries.smm.tester.connectors.hi.SubmitSingleMessageResponse result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * submitSingleMessage operation
   */
  public void receiveErrorsubmitSingleMessage(java.lang.Exception e) {}

  /**
   * auto generated Axis2 call back method for connectivityTest method override this method for
   * handling normal response from connectivityTest operation
   */
  public void receiveResultconnectivityTest(
      org.immregistries.smm.tester.connectors.hi.ConnectivityTestResponse result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * connectivityTest operation
   */
  public void receiveErrorconnectivityTest(java.lang.Exception e) {}



}
