package org.immregistries.smm.tester.connectors;

import java.io.IOException;

import org.immregistries.smm.tester.connectors.al.Test_adphhieStub;

public class ALSoapConnector extends HttpConnector {
  public ALSoapConnector(String label, String url) throws Exception {
    super(label, url, ConnectorFactory.TYPE_AL_SOAP);
  }

  @Override
  public String connectivityTest(String message) throws Exception {
    // TODO Auto-generated method stub
    return "AL ImmPrint SOAP service does not support connectivity test";
  }

  @Override
  public String sendRequest(String request, ClientConnection conn, boolean debug)
      throws IOException {
    try {
      Test_adphhieStub.Aliiashl7Response response = null;
      Test_adphhieStub stub = new Test_adphhieStub(conn.getUrl());
      Test_adphhieStub.Aliiashl7 aiish170 = new Test_adphhieStub.Aliiashl7();
      aiish170.setPayload(request);
      response = stub.aliiashl7(aiish170);
      return response.getPayload();
    } catch (Exception o) {
      o.printStackTrace();
      return "Unable to connect: " + o.getMessage();
    }
  }
}
