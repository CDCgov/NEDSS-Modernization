package org.immregistries.smm.tester.connectors;

import java.io.IOException;

import org.immregistries.smm.tester.connectors.az.asiis_request.aie_schemas.Credentials_type2;
import org.immregistries.smm.tester.connectors.az.asiis_request.aie_schemas.Request_type0;
import org.immregistries.smm.tester.connectors.az.gov.azdhs.www.ASIISStub;
import org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_Submit;
import org.immregistries.smm.tester.connectors.az.gov.azdhs.www.Sync_SubmitResponse;

public class AZSoapConnector extends HttpConnector {
  public AZSoapConnector(String label, String url) throws Exception {
    super(label, url, ConnectorFactory.TYPE_AZ_SOAP);
  }

  @Override
  public String connectivityTest(String message) throws Exception {
    return "AZ web service does not support connectivity test";
  }

  @Override
  public String sendRequest(String request, ClientConnection conn, boolean debug)
      throws IOException {
    try {
      ASIISStub stub = new ASIISStub(conn.getUrl());
      Sync_Submit submit = new Sync_Submit();
      Request_type0 submitRequest = new Request_type0();
      submit.setRequest(submitRequest);
      Credentials_type2 submitRequestCredentials = new Credentials_type2();
      submitRequest.setCredentials(submitRequestCredentials);
      submitRequestCredentials.setUsername(conn.getUserId());
      submitRequestCredentials.setPassword(conn.getPassword());
      submitRequest.setMessage(request);
      Sync_SubmitResponse response = stub.sync_Submit(submit);
      return response.getResponse().getMessage();
    } catch (Exception o) {
      o.printStackTrace();
      return "Unable to connect: " + o.getMessage();
    }
  }
}
