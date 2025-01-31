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

public class MOConnector extends HttpConnector {

  private static final String HL7_REQUEST_RESULT_START_TAG = "<SMVHL7VAL_x0028__x0029_Result>";
  private static final String HL7_REQUEST_RESULT_END_TAG = "</SMVHL7VAL_x0028__x0029_Result>";

  protected MOConnector(String label, String url, String type) {
    super(label, url, type);
    this.url = url;
  }

  public MOConnector(String label, String url) {
    super(label, url, ConnectorFactory.TYPE_MO_SOAP);
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
    result = result.replaceAll("\\Q&amp;\\E", "&");
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
      sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      sb.append(
          "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:prov=\"http://tempuri.org/SMVAX_ProviderInterface_EXT_WS/ProviderInterface_EXT_WS\">");
      sb.append("  <soap:Header>");
      sb.append("    <prov:HL7SoapHeader>");
      sb.append("      <prov:USERID>" + userid + "</prov:USERID>");
      sb.append("      <prov:PASSWORD1>" + password + "</prov:PASSWORD1>");
      sb.append("      <prov:PASSWORD2>" + otherid + "</prov:PASSWORD2>");
      sb.append("      <prov:FACILITYID>" + facilityid + "</prov:FACILITYID>");
      sb.append("      <prov:MESSAGEDATA>" + replaceAmpersand(request) + "</prov:MESSAGEDATA>");
      sb.append("    </prov:HL7SoapHeader>");
      sb.append("  </soap:Header>");
      sb.append("  <soap:Body>");
      // sb.append(" <prov:SMVHL7_x0028__x0029_/>"); // This one works, but has the same ACK for
      // everything I submitted.
      sb.append("    <prov:SMVHL7VAL_x0028__x0029_/>"); // This one currently times out, but was the
                                                        // suggested one by MO.
      sb.append("  </soap:Body>");
      sb.append("</soap:Envelope>");

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
      int endPos = responseString.indexOf(HL7_REQUEST_RESULT_END_TAG);
      if (startPos > 0 && endPos > startPos) {
        responseString =
            responseString.substring(startPos + HL7_REQUEST_RESULT_START_TAG.length(), endPos);
        response = new StringBuilder(responseString);
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
    return "Not supported by MO SOAP interface";
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // TODO Auto-generated method stub
  }

}
