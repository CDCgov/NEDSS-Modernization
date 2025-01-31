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
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 * @author nathan
 */
public class CASoapConnector extends HttpConnector {

  public CASoapConnector(String label, String url) {
    super(label, url, "CA SOAP");
    super.setSetupGlobalKeyStore(false);
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    System.setProperty("https.protocols", "TLSv1");
    ClientConnection cc = new ClientConnection();
    cc.setUserId(userid);
    cc.setPassword(password);
    cc.setFacilityId(facilityid);
    cc.setUrl(url);
    String result = "";
    try {
      result = sendRequest(message, cc, debug);
    } catch (Exception e) {
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
    return result;
  }

  public String sendRequest(String request, ClientConnection conn, boolean debug) {
    StringBuilder response = new StringBuilder();

    StringBuilder debugLog = null;
    String messageBeingSent = null;
    if (debug) {
      debugLog = new StringBuilder();
    }
    try {
      SSLSocketFactory factory = setupSSLSocketFactory(debug, debugLog);
      URLConnection urlConn;
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

      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      urlConn.setRequestProperty("Content-Type", "text/xml;charset=UTF-8");
      urlConn.setRequestProperty("SOAPAction", "\"urn:cdc:iisb:2011:submitSingleMessage\"");
      printout = new DataOutputStream(urlConn.getOutputStream());
      StringWriter stringWriter = new StringWriter();
      PrintWriter out = new PrintWriter(stringWriter);
      // out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      out.println(
          "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:cdc:iisb:2011\">");
      out.println("  <soap:Header/>");
      out.println("    <soap:Body>");
      out.println("    <urn:submitSingleMessage> ");
      out.println("      <urn:username>" + conn.getUserId() + "</urn:username>");
      out.println("      <urn:password>" + conn.getPassword() + "</urn:password>");
      out.println("      <urn:facilityID>" + conn.getFacilityId() + "</urn:facilityID>");
      out.print("      <urn:hl7Message><![CDATA[");
      out.print(request);
      out.println("]]></urn:hl7Message>");
      out.println("    </urn:submitSingleMessage>");
      out.println("  </soap:Body>");
      out.println("</soap:Envelope>");
      messageBeingSent = stringWriter.toString();
      printout.writeBytes(messageBeingSent);
      printout.flush();
      printout.close();
      input = new InputStreamReader(urlConn.getInputStream());
      BufferedReader in = new BufferedReader(input);
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      input.close();
    } catch (IOException ioe) {
      response.append("Unable to relay message, received this error: " + ioe.getMessage());
    }
    if (debug) {
      response.append("\r");
      if (messageBeingSent != null) {
        response.append("SOAP SENT: \r");
        response.append(messageBeingSent);
      }
      response.append("DEBUG Statements: \r");
      response.append(debugLog);
    }
    return response.toString();
  }

  @Override
  public String connectivityTest(String message) throws Exception {
    StringBuilder response = new StringBuilder();

    StringBuilder debugLog = new StringBuilder();
    String messageBeingSent = null;
    try {
      SSLSocketFactory factory = setupSSLSocketFactory(false, debugLog);
      URLConnection urlConn;
      DataOutputStream printout;
      InputStreamReader input = null;
      URL url = new URL(this.url);
      urlConn = url.openConnection();
      if (factory != null && urlConn instanceof HttpsURLConnection) {
        ((HttpsURLConnection) urlConn).setSSLSocketFactory(factory);
      }

      urlConn.setDoInput(true);
      urlConn.setDoOutput(true);
      urlConn.setUseCaches(false);
      urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
      urlConn.setRequestProperty("SOAPAction", "\"urn:cdc:iisb:2011:connectivityTest\"");
      printout = new DataOutputStream(urlConn.getOutputStream());
      StringWriter stringWriter = new StringWriter();
      PrintWriter out = new PrintWriter(stringWriter);
      // out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      out.println(
          "<soap:Envelope xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:cdc:iisb:2011\">");
      out.println("  <soap:Header/>");
      out.println("    <soap:Body>");
      out.println("    <urn:connectivityTest> ");
      out.println("      <urn:echoBack>" + message + "</urn:echoBack>");
      out.println("    </urn:connectivityTest>");
      out.println("  </soap:Body>");
      out.println("</soap:Envelope>");
      messageBeingSent = stringWriter.toString();
      printout.writeBytes(messageBeingSent);
      printout.flush();
      printout.close();
      input = new InputStreamReader(urlConn.getInputStream());
      BufferedReader in = new BufferedReader(input);
      String line;
      while ((line = in.readLine()) != null) {
        response.append(line);
        response.append('\r');
      }
      input.close();

    } catch (IOException ioe) {
      response.append("Unable to relay message, received this error: " + ioe.getMessage());
    }
    return response.toString();
  }

  @Override
  protected void makeScriptAdditions(StringBuilder sb) {
    // do nothing
  }

  @Override
  protected void setupFields(List<String> fields) {
    // do nothing
  }
}
