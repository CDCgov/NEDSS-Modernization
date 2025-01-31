/*
 * To change this template, choose Tools | Templates and open the template in the editor.
 */
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

/**
 * 
 * @author nathan
 */
public class EnvisionConnector extends HttpConnector {

  private static final String HL7_REQUEST_RESULT_START_TAG = "<ExecuteHL7MessageResult>";
  private static final String HL7_REQUEST_RESULT_END_TAG = "</ExecuteHL7MessageResult>";

  private static final String HL7_CONNECTIVITY_RESULT_START_TAG = "<connectivityTestResult>";
  private static final String HL7_CONNECTIVITY_RESULT_END_TAG = "</connectivityTestResult>";

  private boolean useSoap11 = false;

  public EnvisionConnector(String label, String url) throws Exception {
    super(label, url, ConnectorFactory.TYPE_ENVISION_SOAP);
  }

  public EnvisionConnector(String label, String url, boolean useSoap11) throws Exception {
    super(label, url, ConnectorFactory.TYPE_ENVISION_SOAP11);
    this.useSoap11 = useSoap11;
    super.setSetupGlobalKeyStore(false);
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

    HttpURLConnection urlConn = null;
    SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);

    DataOutputStream printout;
    InputStreamReader input = null;
    URL url = new URL(conn.getUrl());

    urlConn = (HttpURLConnection) url.openConnection();
    if (factory != null && urlConn instanceof HttpsURLConnection) {
      ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
    }

    urlConn.setRequestMethod("POST");

    if (useSoap11) {
      urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      urlConn.setRequestProperty("SOAPAction", "http://tempuri.org/ExecuteHL7Message");
    } else {
      urlConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
    }
    urlConn.setDoInput(true);
    urlConn.setDoOutput(true);
    String content;

    StringBuilder sb = new StringBuilder();
    if (useSoap11) {
      sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      sb.append(
          "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">");
      sb.append("  <soap:Body>");
      sb.append("    <ExecuteHL7Message xmlns=\"http://tempuri.org/\">");
      sb.append("      <userName>" + userid + "</userName>");
      sb.append("      <password>" + password + "</password>");
      sb.append("      <flatWire>" + replaceAmpersand(request) + "</flatWire>");
      sb.append("    </ExecuteHL7Message>");
      sb.append("  </soap:Body>");
      sb.append("</soap:Envelope>");
    } else {
      sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      sb.append(
          "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
      sb.append("  <soap12:Body>");
      sb.append("    <ExecuteHL7Message xmlns=\"http://tempuri.org/\">");
      sb.append("      <userName>" + userid + "</userName>");
      sb.append("      <password>" + password + "</password>");
      sb.append("      <flatWire>" + replaceAmpersand(request) + "</flatWire>");
      sb.append("    </ExecuteHL7Message>");
      sb.append("  </soap12:Body>");
      sb.append("</soap12:Envelope>");
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
    int endPos = responseString.indexOf(HL7_REQUEST_RESULT_END_TAG);
    if (startPos > 0 && endPos > startPos) {
      responseString =
          responseString.substring(startPos + HL7_REQUEST_RESULT_START_TAG.length(), endPos);
      responseString = responseString.replaceAll("\\Q&amp;\\E", "&").replaceAll("\\Q&lt;\\E", "<").replaceAll("\\Q&gt;\\E", ">");
      response = new StringBuilder();
      boolean found226 = false;
      boolean found8364 = false;
      for (int i = 0; i < responseString.length(); i++) {
        char c = responseString.charAt(i);
        if (c == 226) {
          found226 = true;
        } else if (found226 && c == 8364) {
          found8364 = true;
        } else if (found8364) {
          if ((c == 339 || c == 65533)) {
            response.append("\"");
          } else {
            response.append("??" + ((int) c) + "??");
          }
          found226 = false;
          found8364 = false;
        } else if (c < 256) {
          response.append(c);
          found226 = false;
          found8364 = false;
        } else {
          response.append("??" + ((int) c) + "??");
        }
      }
    }
    if (debug) {
      response.append("\r");
      response.append("DEBUG LOG: \r");
      response.append(debugLog);
    }
    return response.toString();

  }

  public String sendConnectivityTest(String message, ClientConnection conn, boolean debug)
      throws IOException {
    if (useSoap11) {
      return "Not supported";
    }
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

      urlConn.setRequestProperty("Content-Type", "application/soap+xml; charset=utf-8");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      String content;

      StringBuilder sb = new StringBuilder();
      sb.append("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      sb.append(
          "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">");
      sb.append("  <soap12:Body>");
      sb.append("    <connectivityTest xmlns=\"http://tempuri.org/\">");
      sb.append("      <echoBack>" + replaceAmpersand(message) + "</echoBack>");
      sb.append("    </connectivityTest>");
      sb.append("  </soap12:Body>");
      sb.append("</soap12:Envelope>");
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
      int startPos = responseString.indexOf(HL7_CONNECTIVITY_RESULT_START_TAG);
      int endPos = responseString.indexOf(HL7_CONNECTIVITY_RESULT_END_TAG);
      if (startPos > 0 && endPos > startPos) {
        responseString =
            responseString.substring(startPos + HL7_CONNECTIVITY_RESULT_START_TAG.length(), endPos);
        response = new StringBuilder(responseString);
      }
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
    ClientConnection cc = new ClientConnection();
    cc.setUrl(url);
    String result = "";
    try {
      result = sendConnectivityTest(message, cc, false);
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

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // do nothing
  }

  @Override
  protected void setupFields(List<String> fields) {
    super.setupFields(fields);

  }

}
