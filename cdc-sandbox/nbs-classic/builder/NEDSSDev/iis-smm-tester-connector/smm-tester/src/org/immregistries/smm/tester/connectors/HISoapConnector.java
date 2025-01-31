/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.util.List;

import org.immregistries.smm.tester.connectors.hi.Client_Service;
import org.immregistries.smm.tester.connectors.hi.Client_ServiceStub;
import org.immregistries.smm.tester.connectors.hi.ConnectivityTest;
import org.immregistries.smm.tester.connectors.hi.ConnectivityTestRequestType;
import org.immregistries.smm.tester.connectors.hi.ConnectivityTestResponse;
import org.immregistries.smm.tester.connectors.hi.SubmitSingleMessage;
import org.immregistries.smm.tester.connectors.hi.SubmitSingleMessageRequestType;
import org.immregistries.smm.tester.connectors.hi.SubmitSingleMessageResponse;



/**
 * 
 * @author nathan
 */
public class HISoapConnector extends HttpConnector {

  private Client_Service clientService = null;


  public HISoapConnector(String label, String url) throws Exception {
    super(label, "HI SOAP");
    this.url = url;
    clientService = new Client_ServiceStub(this.url);
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {


    SubmitSingleMessage submitSingleMessage = new SubmitSingleMessage();
    SubmitSingleMessageRequestType request = new SubmitSingleMessageRequestType();
    request.setFacilityID(this.facilityid);
    request.setHl7Message(message);
    request.setPassword(this.password);
    request.setUsername(this.userid);
    submitSingleMessage.setSubmitSingleMessage(request);
    SubmitSingleMessageResponse response = clientService.submitSingleMessage(submitSingleMessage);
    return response.getSubmitSingleMessageResponse().get_return();

  }

  @Override
  public String connectivityTest(String message) throws Exception {
    ConnectivityTestRequestType request = new ConnectivityTestRequestType();
    request.setEchoBack(message);
    ConnectivityTest connectivityTest = new ConnectivityTest();
    connectivityTest.setConnectivityTest(request);
    ConnectivityTestResponse response = clientService.connectivityTest(connectivityTest);
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
