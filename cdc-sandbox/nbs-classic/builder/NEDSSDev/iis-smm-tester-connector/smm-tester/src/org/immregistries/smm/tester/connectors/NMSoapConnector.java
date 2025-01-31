/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
package org.immregistries.smm.tester.connectors;

import java.util.List;

import org.immregistries.smm.tester.connectors.nm.RhapWSUAT_EHR_IIS;
import org.immregistries.smm.tester.connectors.nm.RhapWSUAT_EHR_IISStub;
import org.immregistries.smm.tester.connectors.tlep.ConnectivityTestRequestType;



/**
 * 
 * @author nathan
 */
public class NMSoapConnector extends HttpConnector {

  private RhapWSUAT_EHR_IIS clientService = null;


  public NMSoapConnector(String label, String url) throws Exception {
    super(label, "NM SOAP");
    this.url = url;
    clientService = new RhapWSUAT_EHR_IISStub(this.url);
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {


    org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessage submitSingleMessage =
        new org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessage();
    org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessageRequestType request =
        new org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessageRequestType();
    request.setFacilityID(this.facilityid);
    request.setHl7Message(message);
    request.setPassword(this.password);
    request.setUsername(this.userid);
    submitSingleMessage.setSubmitSingleMessage(request);
    org.immregistries.smm.tester.connectors.tlep.SubmitSingleMessageResponse response =
        clientService.submitSingleMessage(submitSingleMessage);
    return response.getSubmitSingleMessageResponse().get_return();

  }

  @Override
  public String connectivityTest(String message) throws Exception {
    ConnectivityTestRequestType request = new ConnectivityTestRequestType();
    request.setEchoBack(message);
    org.immregistries.smm.tester.connectors.tlep.ConnectivityTest connectivityTest =
        new org.immregistries.smm.tester.connectors.tlep.ConnectivityTest();
    connectivityTest.setConnectivityTest(request);
    org.immregistries.smm.tester.connectors.tlep.ConnectivityTestResponse response =
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
