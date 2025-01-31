package org.immregistries.smm.tester.connectors;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

public class NJConnector extends HttpConnector {

  private static final String HL7_REQUEST_RESULT_START_TAG = "ResponseHL7Message>";
  private static final String HL7_REQUEST_RESULT_END_TAG = "</";

  protected NJConnector(String label, String url, String type) {
    super(label, url, type);
    this.url = url;
  }

  public NJConnector(String label, String url) {
    super(label, url, ConnectorFactory.TYPE_NJ_SOAP);
    this.url = url;
  }

  @Override
  protected void setupFields(List<String> fields) {
    // TODO Auto-generated method stub

  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    ClientConnection cc = new ClientConnection();
    cc.setUrl(url);
    String result = "";
    try {
      result = sendRequest(message, cc, debug);
    } catch (Exception e) {
      if (throwExceptions) {
        throw e;
      }
      return "Unable to relay message, received this error: " + e.getMessage();
    }

    StringBuffer sbuf = new StringBuffer(result.length());
    boolean inTag = false;
    for (char c : result.toCharArray()) {
      if (c == '<') {
        inTag = true;
      } else if (c == '>') {
        inTag = false;
      } else if (!inTag) {
        sbuf.append(c);
      }
    }
    if (sbuf.length() > 0) {
      result = sbuf.toString();
    }

    while (result != null && result.length() > 0 && result.charAt(0) < ' ') {
      result = result.substring(1);
    }
    return result;

  }

  public String sendRequest(String request, ClientConnection conn, boolean debug)
      throws IOException {
    StringBuilder debugLog = null;
    if (debug) {
      debugLog = new StringBuilder();
    }
    try {
      // SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);

      HttpURLConnection urlConn;
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(conn.getUrl());

      urlConn = (HttpURLConnection) url.openConnection();

      urlConn.setRequestMethod("POST");

      urlConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      String content;

      StringBuilder sb = new StringBuilder();
      if (request.indexOf("|QBP^") > 0) {
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\">");
        sb.append("<S:Body>");
        sb.append(
            "<ns4:NJIISIMSGetRequest xmlns:ns4=\"http://njiis.njdoh.gov/ims/service/schema/hl7/get/request\">");
        sb.append("<ns4:RequestHeader>");
        sb.append("<ns4:FacilityID>" + userid + "</ns4:FacilityID>");
        sb.append("<ns4:FacilityKey>" + password + "</ns4:FacilityKey>");
        sb.append("</ns4:RequestHeader>");
        sb.append("<ns4:GetRequest>");
        sb.append("<ns4:NJIISProviderID>" + facilityid + "</ns4:NJIISProviderID>");
        sb.append("<ns4:HL7MessageVersion>2.5.1</ns4:HL7MessageVersion>");
        sb.append(
            "<ns4:RequestHL7Message>" + replaceAmpersand(request) + "</ns4:RequestHL7Message>");
        sb.append("</ns4:GetRequest>");
        sb.append("</ns4:NJIISIMSGetRequest>");
        sb.append("</S:Body>");
        sb.append("</S:Envelope>");
      } else {
        sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
        sb.append("<S:Envelope xmlns:S=\"http://www.w3.org/2003/05/soap-envelope\">");
        sb.append("<S:Body>");
        sb.append(
            "<ns0:NJIISIMSPutRequest xmlns:ns0=\"http://njiis.njdoh.gov/ims/service/schema/hl7/put/request\">");
        sb.append("<ns0:RequestHeader>");
        sb.append("<ns0:FacilityID>" + userid + "</ns0:FacilityID>");
        sb.append("<ns0:FacilityKey>" + password + "</ns0:FacilityKey>");
        sb.append("</ns0:RequestHeader>");
        sb.append("<ns0:PutRequest>");
        sb.append("<ns0:NJIISProviderID>" + facilityid + "</ns0:NJIISProviderID>");
        sb.append("<ns0:HL7MessageVersion>2.5.1</ns0:HL7MessageVersion>");
        sb.append(
            "<ns0:RequestHL7Message>" + replaceAmpersand(request) + "</ns0:RequestHL7Message>");
        sb.append("</ns0:PutRequest>");
        sb.append("</ns0:NJIISIMSPutRequest>");
        sb.append("</S:Body>");
        sb.append("</S:Envelope>");
      }

      content = sb.toString();

      printout = new DataOutputStream(urlConn.getOutputStream());
      printout.writeBytes(content);
      printout.flush();
      printout.close();
      input = new InputStreamReader(urlConn.getInputStream());
      StringBuilder response = new StringBuilder();
      BufferedReader in = new BufferedReader(input);
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      input.close();
      String responseString = response.toString();
      int startPos = responseString.indexOf(HL7_REQUEST_RESULT_START_TAG);
      if (startPos > 0) {
        int endPos = responseString.indexOf(HL7_REQUEST_RESULT_END_TAG, startPos);
        if (endPos > startPos) {
          responseString =
              responseString.substring(startPos + HL7_REQUEST_RESULT_START_TAG.length(), endPos);
          responseString =
              responseString.replaceAll("\\Q&#xd;\\E", "\r").replaceAll("\\Q&amp;\\E", "&");
          response = new StringBuilder(responseString);
        }
      }
      if (debug) {
        response.append("\r");
        response.append("DEBUG LOG: \r");
        response.append(debugLog);
        response.append("MESSAGE SENT: \r");
        response.append(
            content.replaceAll("\\&", "&amp;").replaceAll("\\<", "&lt;").replaceAll("\\>", "&gt;"));
      }
      return response.toString();
    } catch (IOException e) {
      StringWriter stringWriter = new StringWriter();
      PrintWriter out = new PrintWriter(stringWriter);
      out.println("Unable to complete request");
      e.printStackTrace(out);
      out.println("DEBUG LOG: \r");
      out.println(debugLog);
      out.close();
      return stringWriter.toString();
    }

  }

  @Override
  public String connectivityTest(String message) throws Exception {
    return "NJ ping command not compatible with CDC Connectivity Test";
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // TODO Auto-generated method stub
  }

}
