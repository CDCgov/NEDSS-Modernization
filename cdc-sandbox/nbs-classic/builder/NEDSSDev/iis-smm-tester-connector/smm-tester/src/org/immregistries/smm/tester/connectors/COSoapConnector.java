/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.util.List;

import org.immregistries.smm.tester.connectors.co.Client_ServiceStub;

/**
 * 
 * @author nathan
 */
public class COSoapConnector extends HttpConnector {

  private Client_ServiceStub clientService = null;

  public COSoapConnector(String label, String url) throws Exception {
    super(label, url, ConnectorFactory.TYPE_CO_SOAP);
    clientService = new Client_ServiceStub(this.url);
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    Client_ServiceStub.SubmitSingleMessage submitSingleMessage =
        new Client_ServiceStub.SubmitSingleMessage();
    Client_ServiceStub.SubmitSingleMessageRequestType request =
        new Client_ServiceStub.SubmitSingleMessageRequestType();
    request.setFacilityID(this.facilityid);
    request.setHl7Message(message.replaceAll("\\r", "\n"));
    request.setPassword(this.password);
    request.setUsername(this.userid);
    submitSingleMessage.setSubmitSingleMessage(request);
    Client_ServiceStub.SubmitSingleMessageResponse response =
        clientService.submitSingleMessage(submitSingleMessage);

    return response.getSubmitSingleMessageResponse().get_return();

  }

  @Override
  public String connectivityTest(String message) throws Exception {
    Client_ServiceStub.ConnectivityTestRequestType request =
        new Client_ServiceStub.ConnectivityTestRequestType();
    request.setEchoBack(message);
    Client_ServiceStub.ConnectivityTest connectivityTest =
        new Client_ServiceStub.ConnectivityTest();
    connectivityTest.setConnectivityTest(request);
    Client_ServiceStub.ConnectivityTestResponse response =
        clientService.connectivityTest(connectivityTest);
    return response.getConnectivityTestResponse().get_return();
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // do nothing
  }

  @Override
  protected void setupFields(List<String> fields) {
    super.setupFields(fields);

  }

}
