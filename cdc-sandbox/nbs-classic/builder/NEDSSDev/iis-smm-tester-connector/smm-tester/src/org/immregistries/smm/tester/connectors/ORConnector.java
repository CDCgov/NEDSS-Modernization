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
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.codec.binary.Base64;

/**
 * 
 * @author nathan
 */
public class ORConnector extends HttpConnector {

  private static String XML_START_1 =
      "<soap:Envelope xmlns:soap=\"http://www.w3.org/2003/05/soap-envelope\" xmlns:vac=\"http://vaccination.org/\">";
  private static String XML_START_2a =
      "<soap:Header xmlns:wsa=\"http://www.w3.org/2005/08/addressing\"><wsse:Security soap:mustUnderstand=\"true\" xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\" xmlns:wsu=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-utility-1.0.xsd\"><wsse:UsernameToken wsu:Id=\"UsernameToken-";
  private static String XML_START_2b = "\"><wsse:Username>";
  private static String XML_START_3 =
      "</wsse:Username><wsse:Password Type=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-username-token-profile-1.0#PasswordText\">";
  private static String XML_START_4a =
      "</wsse:Password><wsse:Nonce EncodingType=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-soap-message-security-1.0#Base64Binary\">";
  private static String XML_START_4b = "</wsse:Nonce><wsu:Created>";
  private static String XML_START_5 =
      "</wsu:Created></wsse:UsernameToken></wsse:Security><wsa:Action>http://vaccination.org/IVaccinationService/UpdateHistoryRequest</wsa:Action><wsa:MessageID>";
  private static String XML_START_6 =
      "</wsa:MessageID></soap:Header><soap:Body><vac:UpdateHistory><arg0><![CDATA[";
  private static String XML_END = "]]></arg0></vac:UpdateHistory></soap:Body></soap:Envelope>";

  private static final String HL7_REQUEST_RESULT_START_TAG = "<return>";
  private static final String HL7_REQUEST_RESULT_END_TAG = "</return>";

  protected ORConnector(String label, String url, String type) {
    super(label, url, type);
    this.url = url;
    super.setSetupGlobalKeyStore(false);
  }

  public ORConnector(String label, String url) {
    super(label, url, "OR SOAP");
    this.url = url;
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
    URLConnection urlConn = null;
    if (debug) {
      debugLog = new StringBuilder();
    }
    try {
      SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(conn.getUrl());
      urlConn = url.openConnection();
      if (factory != null && urlConn instanceof HttpsURLConnection) {
        if (debug) {
          debugLog.append("Using custom factory for SSL \r");
        }
        ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
      }

      ((HttpURLConnection) urlConn).setRequestMethod("POST");

      urlConn.setRequestProperty("Content-Type",
          "application/soap+xml; charset=utf-8;action=\"http://vaccination.org/IVaccinationService/UpdateHistoryRequest\"");
      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      String content;

      SimpleDateFormat sdfDate = new SimpleDateFormat("yyyy-MM-dd"); // 2011-12-22T21:55:18.593Z
      SimpleDateFormat sdfTime = new SimpleDateFormat("HH:mm:ss.SSS");
      sdfTime.setTimeZone(TimeZone.getTimeZone("GMT"));
      sdfDate.setTimeZone(TimeZone.getTimeZone("GMT"));
      Date today = new Date();
      String messageDate = sdfDate.format(today) + "T" + sdfTime.format(today) + "Z";
      StringBuilder sb = new StringBuilder();
      sb.append(XML_START_1);
      sb.append(XML_START_2a);
      sb.append(randomHex(32));
      sb.append(XML_START_2b);
      sb.append(userid);
      sb.append(XML_START_3);
      sb.append(password);
      sb.append(XML_START_4a);
      sb.append(Base64.encodeBase64(randomHex(16).getBytes()));
      sb.append(XML_START_4b);
      sb.append(messageDate);
      sb.append(XML_START_5);
      sb.append("uuid:" + randomHex(8) + "-" + randomHex(4) + "-" + randomHex(4) + "-"
          + randomHex(4) + "-" + randomHex(12));
      sb.append(XML_START_6);
      sb.append(request);
      sb.append(XML_END);
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
      responseString = responseString.replaceAll("\\Q&amp;\\E", "&");
      if (debug) {
        response = new StringBuilder(responseString);
        response.append("\r");
        response.append("DEBUG LOG: \r");
        response.append(debugLog);
        responseString = response.toString();
      }
      return responseString;
    } catch (IOException e) {
      e.printStackTrace(System.out);
      if (urlConn != null) {
        InputStreamReader input =
            new InputStreamReader(((HttpURLConnection) urlConn).getErrorStream());
        BufferedReader in = new BufferedReader(input);
        String line;
        while ((line = in.readLine()) != null) {
          System.out.println(line);
        }
        input.close();
      }
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
    return "Not supported yet";
  }

  @Override
  protected void setupFields(List<String> fields) {
      // nothing to do
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // nothing to do
  }

  Random random = new Random();

  private String randomHex(int length) {
    String s = "";
    for (int i = 0; i < length; i++) {
      int v = random.nextInt(16);
      if (v < 10) {
        s += v;
      } else {
        s += (char) (((int) 'A') + (v - 10));
      }
    }
    return s;
  }
}
