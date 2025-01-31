/**
 * Test_adphhieCallbackHandler.java
 *
 * This file was auto-generated from WSDL by the Apache Axis2 version: 1.6.3 Built on : Jun 27, 2015
 * (11:17:49 BST)
 */
package org.immregistries.smm.tester.connectors.al;


/**
 * Test_adphhieCallbackHandler Callback class, Users can extend this class and implement their own
 * receiveResult and receiveError methods.
 */
public abstract class Test_adphhieCallbackHandler {
  protected Object clientData;

  /**
   * User can pass in any object that needs to be accessed once the NonBlocking Web service call is
   * finished and appropriate method of this CallBack is called.
   * 
   * @param clientData Object mechanism by which the user can pass in user data that will be
   *        avilable at the time this callback is called.
   */
  public Test_adphhieCallbackHandler(Object clientData) {
    this.clientData = clientData;
  }

  /**
   * Please use this constructor if you don't want to set any clientData
   */
  public Test_adphhieCallbackHandler() {
    this.clientData = null;
  }

  /**
   * Get the client data
   */
  public Object getClientData() {
    return clientData;
  }

  /**
   * auto generated Axis2 call back method for aliiashl7 method override this method for handling
   * normal response from aliiashl7 operation
   */
  public void receiveResultaliiashl7(
      org.immregistries.smm.tester.connectors.al.Test_adphhieStub.Aliiashl7Response result) {}

  /**
   * auto generated Axis2 Error handler override this method for handling error response from
   * aliiashl7 operation
   */
  public void receiveErroraliiashl7(java.lang.Exception e) {}
}
