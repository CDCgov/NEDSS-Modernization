/**
 * HL7WSCallbackHandler.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.ks;


/**
 * HL7WSCallbackHandler Callback class, Users can extend this class and implement their own
 * receiveResult and receiveError methods.
 */
public abstract class HL7WSCallbackHandler {
  protected Object clientData;

  /**
   * User can pass in any object that needs to be accessed once the NonBlocking Web service call is
   * finished and appropriate method of this CallBack is called.
   * 
   * @param clientData Object mechanism by which the user can pass in user data that will be
   *        avilable at the time this callback is called.
   */
  public HL7WSCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }

  /**
   * Please use this constructor if you don't want to set any clientData
   */
  public HL7WSCallbackHandler() {
    this.clientData = null;
  }

  /**
   * Get the client data
   */
  public Object getClientData() {
    return clientData;
  }

  /**
   * auto generated Axis2 call back method for processHL7Message method override this method for
   * handling normal response from processHL7Message operation
   */
  public void receiveResultprocessHL7Message(
      org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * processHL7Message operation
   */
  public void receiveErrorprocessHL7Message(java.lang.Exception e) {}
}
