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

/**
 * 
 * @author nathan
 */
public class ILSoapConnector extends Connector {

  public ILSoapConnector(String label, String url) {
    super(label, ConnectorFactory.TYPE_IL_SOAP);
    this.url = url;
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    ClientConnection cc = new ClientConnection();
    cc.setUserId(userid);
    cc.setPassword(password);
    cc.setFacilityId(facilityid);
    cc.setUrl(url);
    String result = "";
    try {

      result = sendRequest(message, cc);
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

  public String sendRequest(String request, ClientConnection conn) throws IOException {
    URLConnection urlConn;
    DataOutputStream printout;
    InputStreamReader input = null;
    URL url = new URL(conn.getUrl());
    urlConn = url.openConnection();
    urlConn.setDoInput(true);
    urlConn.setDoOutput(true);
    urlConn.setUseCaches(false);
    urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
    printout = new DataOutputStream(urlConn.getOutputStream());
    StringWriter stringWriter = new StringWriter();
    PrintWriter out = new PrintWriter(stringWriter);
    out.println("<?xml version='1.0' encoding='UTF-8'?>");
    out.println("<Envelope xmlns=\"http://www.w3.org/2003/05/soap-envelope\">");
    out.println("   <Header />");
    out.println("   <Body>");
    out.println("      <submitSingleMessage xmlns=\"urn:cdc:iisb:2011\">");
    out.println("         <username>" + conn.getUserId() + "</username>");
    out.println("         <password>" + conn.getPassword() + "</password>");
    out.println("         <facilityID>" + conn.getFacilityId() + "</facilityID>");
    out.println("         <hl7Message>" + replaceAmpersand(request) + "</hl7Message>");
    out.println("      </submitSingleMessage>");
    out.println("   </Body>");
    out.println("</Envelope>");
    printout.writeBytes(stringWriter.toString());
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
    StringBuilder s = extractResponse(response, "<return>", "</return>");
    return s.toString();
  }

  @Override
  public String connectivityTest(String message) throws Exception {
    URLConnection urlConn;
    DataOutputStream printout;
    InputStreamReader input = null;
    URL url = new URL(this.url);
    urlConn = url.openConnection();
    urlConn.setDoInput(true);
    urlConn.setDoOutput(true);
    urlConn.setUseCaches(false);
    urlConn.setRequestProperty("Content-Type", "text/xml; charset=utf-8");
    printout = new DataOutputStream(urlConn.getOutputStream());
    StringWriter stringWriter = new StringWriter();
    PrintWriter out = new PrintWriter(stringWriter);

    out.println("<?xml version='1.0' encoding='UTF-8'?>");
    out.println("<Envelope xmlns=\"http://www.w3.org/2003/05/soap-envelope\">");
    out.println("  <Header />");
    out.println("  <Body>");
    out.println("    <connectivityTest xmlns=\"urn:cdc:iisb:2011\">");
    out.println("      <echoBack>" + message + "</echoBack>   ");
    out.println("    </connectivityTest>");
    out.println("  </Body>");
    out.println("</Envelope>");

    printout.writeBytes(stringWriter.toString());
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
    StringBuilder s = extractResponse(response, "<return>", "</return>");
    return s.toString();
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
