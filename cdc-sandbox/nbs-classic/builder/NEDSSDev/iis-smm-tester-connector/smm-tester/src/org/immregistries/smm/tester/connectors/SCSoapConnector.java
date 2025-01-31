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
import java.util.List;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLSocketFactory;

/**
 * 
 * @author nathan
 */
public class SCSoapConnector extends HttpConnector {

  private static final String RESULT_BEG = "<SubmitSingleMessageResult>";
  private static final String RESULT_END = "</SubmitSingleMessageResult>";

  public SCSoapConnector(String label, String url) {
    super(label, url, "SC SOAP");
  }

  @Override
  public String submitMessage(String message, boolean debug) throws Exception {
    // System.setProperty("https.protocols", "TLSv1");
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
      urlConn.setRequestProperty("SOAPAction",
          "\"http://dhec.sc.gov/HL7/WebServices/ProcessMessage/IMessageService/SubmitSingleMessage\"");
      printout = new DataOutputStream(urlConn.getOutputStream());
      StringWriter stringWriter = new StringWriter();
      PrintWriter out = new PrintWriter(stringWriter);
      out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      out.println(
          "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://dhec.sc.gov/HL7/WebServices/ProcessMessage/\">");
      out.println("  <soapenv:Header/>");
      out.println("  <soapenv:Body>");
      out.println("    <proc:SubmitSingleMessage> ");
      out.println("      <proc:username>" + conn.getUserId() + "</proc:username>");
      out.println("      <proc:password>" + conn.getPassword() + "</proc:password>");
      out.println("      <proc:facilityID>" + conn.getFacilityId() + "</proc:facilityID>");
      out.println("        <proc:hl7Message>" + replaceAmpersand(request) + "</proc:hl7Message>");
      out.println("    </proc:SubmitSingleMessage>");
      out.println("  </soapenv:Body>");
      out.println("</soapenv:Envelope>");
      messageBeingSent = stringWriter.toString();
      printout.writeBytes(messageBeingSent);
      printout.flush();
      printout.close();
      try {
        HttpURLConnection httpConn = (HttpURLConnection) urlConn;
        if (httpConn.getResponseCode() == 200) {
          input = new InputStreamReader(httpConn.getInputStream());
        } else {
          input = new InputStreamReader(httpConn.getErrorStream());
        }
        BufferedReader in = new BufferedReader(input);
        String line;
        while ((line = in.readLine()) != null) {
          response.append(line);
          response.append('\r');
        }

        String responseString = response.toString();
        int posBeg = responseString.indexOf(RESULT_BEG);
        if (posBeg > 0) {
          posBeg += RESULT_BEG.length();
          int posEnd = responseString.indexOf(RESULT_END, posBeg);
          if (posEnd > posBeg) {
            responseString = responseString.substring(posBeg, posEnd);
            responseString = responseString.replaceAll("\\Q&amp;\\E", "&");
            responseString = responseString.replaceAll("\\Q&#xD;\\E", "\r");
            response = new StringBuilder(responseString);
          }
        }
        input.close();
      } catch (IOException ioe) {
        ioe.printStackTrace();
        throw ioe;
      }

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
      urlConn.setRequestProperty("SOAPAction",
          "\"http://dhec.sc.gov/HL7/WebServices/ProcessMessage/IMessageService/ConnectivityTest\"");
      printout = new DataOutputStream(urlConn.getOutputStream());
      StringWriter stringWriter = new StringWriter();
      PrintWriter out = new PrintWriter(stringWriter);
      out.println("<?xml version=\"1.0\" encoding=\"utf-8\"?>");
      out.println(
          "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:proc=\"http://dhec.sc.gov/HL7/WebServices/ProcessMessage/\">");
      out.println("  <soapenv:Header/>");
      out.println("    <soapenv:Body>");
      out.println("    <proc:ConnectivityTest> ");
      out.println("      <proc:echoBack>" + message + "</proc:echoBack>");
      out.println("    </proc:ConnectivityTest>");
      out.println("  </soapenv:Body>");
      out.println("</soapenv:Envelope>");
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
