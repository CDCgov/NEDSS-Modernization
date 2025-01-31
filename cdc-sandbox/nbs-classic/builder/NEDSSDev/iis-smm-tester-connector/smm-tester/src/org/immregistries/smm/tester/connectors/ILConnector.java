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

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

public class ILConnector extends HttpConnector {

  private static final String HL7_REQUEST_RESULT_START_TAG = "<HL7RequestResult>";
  private static final String HL7_REQUEST_RESULT_END_TAG = "</HL7RequestResult>";

  protected ILConnector(String label, String url, String type) {
    super(label, url, type);
    this.url = url;
    super.setSetupGlobalKeyStore(false);
  }

  public ILConnector(String label, String url) {
    super(label, url, ConnectorFactory.TYPE_IL_WS);
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
      SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);

      HttpURLConnection urlConn;
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(conn.getUrl());

      urlConn = (HttpURLConnection) url.openConnection();
      if (factory != null && urlConn instanceof HttpsURLConnection) {
        ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
      }

      urlConn.setRequestMethod("POST");

      urlConn.setRequestProperty("Accept", null);
      urlConn.setRequestProperty("Accept-Encoding", "gzip, deflate");
      urlConn.setRequestProperty("Expect", "100-continue");
      urlConn.setRequestProperty("Content-Type",
          "application/soap+xml; charset=UTF-8; action=\"http://HL7_ICARE/HL7Exchange/HL7Request\"");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      String content;

      StringBuilder sb = new StringBuilder();
      sb.append(
          "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:hl7=\"http://HL7_ICARE\">");
      sb.append("<soap:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\">");
      sb.append("<wsa:To>https://icarehl7.dph.illinois.gov</wsa:To>");
      sb.append("<wsa:Action>http://HL7_ICARE/HL7Exchange/HL7Request</wsa:Action></soap:Header>");
      sb.append("<soap:Body>");
      sb.append("<hl7:HL7Request>");
      sb.append("<hl7:username>");
      sb.append(userid);
      sb.append("</hl7:username>");
      sb.append("<hl7:password>");
      sb.append(password);
      sb.append("</hl7:password>");
      sb.append("<hl7:HL7Message>");
      sb.append(replaceAmpersand(request));
      sb.append("</hl7:HL7Message>");
      sb.append("</hl7:HL7Request></soap:Body></soap:Envelope>");
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
      String startTag = HL7_REQUEST_RESULT_START_TAG;
      String stopTag = HL7_REQUEST_RESULT_END_TAG;
      response = extractResponse(response, startTag, stopTag);
      if (debug) {
        response.append("\r");
        response.append("DEBUG LOG: \r");
        response.append(debugLog);
      }
      return response.toString();
    } catch (IOException e) {
      if (debug) {
        StringWriter stringWriter = new StringWriter();
        PrintWriter out = new PrintWriter(stringWriter);
        out.println("Unable to complete request");
        e.printStackTrace(out);
        out.println("DEBUG LOG: \r");
        out.println(debugLog);
        out.close();
        return stringWriter.toString();
      } else {
        throw e;
      }
    }

  }


  @Override
  public String connectivityTest(String message) throws Exception {
    return "Not supported by IL WS";
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // TODO Auto-generated method stub
  }


}
