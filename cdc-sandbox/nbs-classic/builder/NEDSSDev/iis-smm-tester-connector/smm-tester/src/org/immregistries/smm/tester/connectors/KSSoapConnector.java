package org.immregistries.smm.tester.connectors;

import java.io.IOException;

import javax.activation.DataHandler;

import org.immregistries.smm.tester.connectors.ks.HL7WSStub;
import org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSRequest;
import org.immregistries.smm.tester.connectors.ks.HL7WSStub.KSResult;

public class KSSoapConnector extends HttpConnector {
  public KSSoapConnector(String label, String url) throws Exception {
    super(label, url, ConnectorFactory.TYPE_KS_SOAP);
  }

  @Override
  public String connectivityTest(String message) throws Exception {
    return "KS web service does not support connectivity test";
  }

  @Override
  public String sendRequest(String request, ClientConnection conn, boolean debug)
      throws IOException {
    try {
      HL7WSStub stub = new HL7WSStub(conn.getUrl());
      KSRequest p = new KSRequest();
      p.setFACILITYID(conn.getFacilityId());
      DataHandler messageData = new DataHandler(request, "text/plain");
      p.setMESSAGEDATA(messageData);
      p.setPASSWORD(conn.getPassword());
      KSResult r = stub.processHL7Message(p);
      return r.getResult();

    } catch (Exception o) {
      o.printStackTrace();
      return "Unable to connect: " + o.getMessage();
    }
  }
}
